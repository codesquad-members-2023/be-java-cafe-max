package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private static final String USER_ID = "userId";
    private static final String PROFILE_FORM = "profileForm";
    private static final String PROFILE_SETTING_FORM = "profileEditForm";
    private static final String USERS = "users";
    private static final String EMAIL = "email";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final JoinFormValidator joinFormValidator;

    public UserController(UserService userService, JoinFormValidator joinFormValidator) {
        this.userService = userService;
        this.joinFormValidator = joinFormValidator;
    }

    private static void loggingError(BindingResult bindingResult) {
        bindingResult.getAllErrors()
                .forEach(error -> logger.error("[ Name = {} ][ Message = {} ]", error.getObjectName(),
                        error.getDefaultMessage()));
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
    public String login(@Valid LoginForm loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            loggingError(bindingResult);
            return "account/login";
        }
        Long userId = userService.login(loginForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "account/login";
        }
        return "redirect:/users/" + userId + "/profile";
    }

    @GetMapping("/users/join")
    public String viewJoinForm(@ModelAttribute JoinForm joinForm) {
        return "account/join";
    }

    @PostMapping("/users")
    public String saveUser(@Valid JoinForm joinForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            loggingError(bindingResult);
            return "account/join";
        }
        int userId = userService.save(joinForm);
        return "redirect:/users/" + userId + "/profile";
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        List<UserForm> allUserForm = userService.getAllUsersForm();
        model.addAttribute(USERS, allUserForm);
        return "account/users";
    }

    @GetMapping("/users/{userId}/profile")
    public String viewUser(Model model, @PathVariable Long userId) {
        User user = userService.findById(userId);
        ProfileForm profileForm = ProfileForm.from(user);

        model.addAttribute(PROFILE_FORM, profileForm);
        model.addAttribute(USER_ID, userId);
        return "account/profile";
    }

    @GetMapping("/users/{userId}/profile/edit")
    public String viewUserProfileEditForm(Model model, @PathVariable Long userId) {
        User user = userService.findById(userId);
        ProfileEditForm profileEditForm = ProfileEditForm.from(user);

        model.addAttribute(USER_ID, userId);
        model.addAttribute(PROFILE_SETTING_FORM, profileEditForm);
        return "account/profileEditForm";
    }

    @PutMapping("/users/{userId}/profile")
    public String updateUserProfile(@Valid ProfileEditForm profileEditForm, BindingResult bindingResult,
                                    @PathVariable Long userId
    ) {
        if (bindingResult.hasErrors()) {
            loggingError(bindingResult);
            return "account/profileEditForm";
        }
        userService.update(profileEditForm, userId, bindingResult);
        if (bindingResult.hasErrors()) {
            return "account/profileEditForm";
        }
        return "redirect:/users/{userId}/profile";
    }
}
