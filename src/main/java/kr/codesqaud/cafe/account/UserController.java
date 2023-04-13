package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private static final String USER_ID = "userId";
    private static final String PROFILE_FORM = "profileForm";
    private static final String PROFILE_SETTING_FORM = "profileEditForm";
    private static final String USERS = "users";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    public static final String ATTRIBUTE_USER = "user";
    private final UserService userService;
    private final JoinFormValidator joinFormValidator;

    public UserController(UserService userService, JoinFormValidator joinFormValidator) {
        this.userService = userService;
        this.joinFormValidator = joinFormValidator;
    }


    @InitBinder(value = "joinForm")
    public void joinFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinFormValidator);
    }

    @GetMapping("/users/login")
    public String viewLoginForm(@ModelAttribute LoginForm loginForm) {
        return "account/login";
    }

    @PostMapping("/users/login")
    public String login(@Valid LoginForm loginForm, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "account/login";
        }
        User user = userService.checkLoginForm(loginForm);
        session.setAttribute(ATTRIBUTE_USER, user);
        return "redirect:/users/" + user.getId() + "/profile";
    }

    @GetMapping("/users/logout")
    public String logout(@ModelAttribute LoginForm loginForm, HttpSession session) {
        session.removeAttribute(ATTRIBUTE_USER);
        return "account/login";
    }

    @GetMapping("/users/join")
    public String viewJoinForm(@ModelAttribute JoinForm joinForm) {
        return "account/join";
    }

    @PostMapping("/users")
    public String saveUser(@Valid JoinForm joinForm, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "account/join";
        }
        User user = userService.save(joinForm);
        session.setAttribute(ATTRIBUTE_USER, user);
        return "redirect:/users/" + user.getId() + "/profile";
    }

    @GetMapping("/users")
    public String viewUsers(Model model, @SessionAttribute User user) {
        userService.checkManager(user);
        List<UserForm> allUserForm = userService.getAllUsersForm();
        model.addAttribute(USERS, allUserForm);
        return "account/users";
    }

    @GetMapping("/users/{userId}/profile")
    public String viewUser(Model model, @PathVariable Long userId, @SessionAttribute User user) {
        userService.checkId(user, userId);
        ProfileForm profileForm = ProfileForm.from(user);
        model.addAttribute(PROFILE_FORM, profileForm);
        model.addAttribute(USER_ID, userId);
        return "account/profile";
    }

    @GetMapping("/users/{userId}/profile/edit")
    public String viewUserProfileEditForm(Model model, @PathVariable Long userId, @SessionAttribute User user) {
        userService.checkId(user, userId);
        ProfileEditForm profileEditForm = ProfileEditForm.from(user);
        model.addAttribute(USER_ID, userId);
        model.addAttribute(PROFILE_SETTING_FORM, profileEditForm);
        return "account/profileEditForm";
    }

    @PutMapping("/users/{userId}/profile")
    public String updateUserProfile(@Valid ProfileEditForm profileEditForm, BindingResult bindingResult,
                                    @PathVariable Long userId, @SessionAttribute User user, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "account/profileEditForm";
        }
        userService.checkId(user, userId);
        if (userService.isDuplicateEmail(user, profileEditForm.getEmail())) {
            bindingResult.rejectValue(EMAIL, "error.email.duplicate");
            return "account/profileEditForm";
        }
        if (!userService.isSamePassword(user, profileEditForm.getPassword())) {
            bindingResult.rejectValue(PASSWORD, "error.password.notMatch");
            return "account/profileEditForm";
        }
        User updateUser = userService.update(user, profileEditForm);
        httpSession.setAttribute(ATTRIBUTE_USER, updateUser);
        return "redirect:/users/{userId}/profile";
    }

}
