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
import java.util.Optional;

@Controller
public class UserController {

    private static final String PASSWORD = "password";
    private static final String USER_ID = "userId";
    private static final String PROFILE_FORM = "profileForm";
    private static final String PROFILE_SETTING_FORM = "profileSettingForm";
    private static final String USERS = "users";
    private static final String EMAIL = "email";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final JoinFormValidator joinFormValidator;

    public UserController(UserService userService,
                          JoinFormValidator joinFormValidator) {
        this.userService = userService;
        this.joinFormValidator = joinFormValidator;
    }

    private static void loggingError(BindingResult bindingResult) {
        bindingResult.getAllErrors()
                .forEach(error -> logger.error("[ Name = {} ][ Message = {} ]", error.getObjectName(),
                        error.getDefaultMessage()));
    }

    @InitBinder("joinForm")
    public void joinFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinFormValidator);
    }

    @GetMapping("/users/login")
    public String showLoginPage(@ModelAttribute LoginForm loginForm) {
        return "account/login";
    }

    @PostMapping("/users/login")
    public String login(@Valid LoginForm loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            loggingError(bindingResult);
            return "account/login";
        }
        Optional<User> userOptional = userService.findByEmail(loginForm.getEmail());
        if (userOptional.isEmpty()) {
            loggingError(bindingResult);
            bindingResult.rejectValue(EMAIL, "error.email.notExist");
            return "account/login";
        }
        User user = userOptional.get();
        if (!user.getPassword().equals(loginForm.getPassword())) {
            loggingError(bindingResult);
            bindingResult.rejectValue(PASSWORD, "error.password.notMatch");
            return "account/login";
        }
        return "redirect:/users/" + user.getId();
    }

    @GetMapping("/users/join")
    public String showJoinPage(@ModelAttribute JoinForm joinForm) {
        return "account/join";
    }

    @PostMapping("/users")
    public String addUser(@Valid JoinForm joinForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            loggingError(bindingResult);
            return "account/join";
        }
        int userId = userService.save(joinForm);
        return "redirect:/users/" + userId+"/profile";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<UserForm> allUserForm = userService.getAllUsersForm();
        model.addAttribute(USERS, allUserForm);
        return "account/members";
    }

    @GetMapping("/users/{userId}/profile")
    public String showUser(Model model, @PathVariable Long userId) {
        User user = userService.findById(userId);
        ProfileForm profileForm = ProfileForm.from(user);

        model.addAttribute(PROFILE_FORM, profileForm);
        model.addAttribute(USER_ID, userId);
        return "account/profile";
    }

    @GetMapping("/users/{userId}/profile/edit")
    public String showUserProfile(Model model, @PathVariable Long userId) {
        User user = userService.findById(userId);
        ProfileSettingForm profileSettingForm = ProfileSettingForm.from(user);

        model.addAttribute(USER_ID, userId);
        model.addAttribute(PROFILE_SETTING_FORM, profileSettingForm);
        return "account/profileUpdate";
    }

    @PutMapping("/users/{userId}/profile")
    public String setUserProfile(@Valid ProfileSettingForm profileSettingForm, BindingResult bindingResult,
                                 @PathVariable Long userId
    ) {
        if (bindingResult.hasErrors()) {
            loggingError(bindingResult);
            return "account/profileUpdate";
        }
        if (userService.isDuplicateEmail(profileSettingForm.getEmail())) {
            bindingResult.rejectValue(EMAIL, "error.email.duplicate");
            loggingError(bindingResult);
            return "account/profileUpdate";
        }
        if (!userService.isSamePassword(userId,profileSettingForm.getPassword())) {
            bindingResult.rejectValue(PASSWORD, "error.password.notMatch");
            loggingError(bindingResult);
            return "account/profileUpdate";
        }
        userService.update(profileSettingForm, userId);
        return "redirect:/users/{userId}/profile";
    }
}
