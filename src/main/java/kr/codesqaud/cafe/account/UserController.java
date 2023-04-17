package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.annotation.ValidUserIdPath;
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

    public static final String ATTRIBUTE_USER = "user";
    private static final String USER_ID = "userId";
    private static final String PROFILE_FORM = "profileForm";
    private static final String PROFILE_SETTING_FORM = "profileEditForm";
    private static final String USERS = "users";
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
    public String viewUsers(Model model) {
        List<UserForm> allUserForm = userService.getAllUsersForm();
        model.addAttribute(USERS, allUserForm);
        return "account/users";
    }

    @ValidUserIdPath
    @GetMapping("/users/{userId}/profile")
    public String viewUser(Model model, @PathVariable Long userId, @SessionAttribute User user) {
        model.addAttribute(PROFILE_FORM, ProfileForm.from(user));
        model.addAttribute(USER_ID, userId);
        return "account/profile";
    }

    @ValidUserIdPath
    @GetMapping("/users/{userId}/profile/edit")
    public String viewUserProfileEditForm(Model model, @PathVariable Long userId, @SessionAttribute User user) {
        model.addAttribute(USER_ID, userId);
        model.addAttribute(PROFILE_SETTING_FORM, ProfileEditForm.from(user));
        return "account/profileEditForm";
    }


    /**
     * @param userId AuthBeforeAdvice 에서 접근 제한을 확인하기 위하여 사용합니다.
     */
    @ValidUserIdPath
    @PutMapping("/users/{userId}/profile")
    public String updateUserProfile(@ModelAttribute @Valid ProfileEditForm profileEditForm, BindingResult bindingResult,
                                    @PathVariable Long userId, @SessionAttribute User user, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "account/profileEditForm";
        }
        userService.checkEditInfo(user, profileEditForm);
        User updateUser = userService.update(user, profileEditForm);
        httpSession.setAttribute(ATTRIBUTE_USER, updateUser);
        return "redirect:/users/{userId}/profile";
    }

}
