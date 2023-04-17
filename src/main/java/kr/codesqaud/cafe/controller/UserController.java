package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.config.SessionAttributeNames;
import kr.codesqaud.cafe.dto.user.UserResponse;
import kr.codesqaud.cafe.dto.user.UserSaveRequest;
import kr.codesqaud.cafe.dto.user.UserUpdateRequest;
import kr.codesqaud.cafe.exception.user.AccessDeniedException;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("userSaveRequest", new UserSaveRequest());
        return "user/sign-up";
    }

    @PostMapping
    public String signUp(@ModelAttribute UserSaveRequest userSaveRequest) {
        userService.saveUser(userSaveRequest);
        return "redirect:/users/login";
    }

    @GetMapping("/{userId}")
    public String showUserProfile(@PathVariable String userId, Model model) {
        UserResponse userResponse = userService.findByUserId(userId);
        model.addAttribute("user", userResponse);

        return "user/profile";
    }

    @GetMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId, Model model, HttpSession httpSession) {
        checkUserPermissions(httpSession, userId);
        UserUpdateRequest userUpdateRequest = userService.makeUserUpdateRequestByUserId(userId);
        model.addAttribute("user", userUpdateRequest);

        return "user/update";
    }

    @PutMapping("/{userId}")
    public String updateUser(@ModelAttribute UserUpdateRequest userUpdateRequest, HttpSession httpSession) {
        checkUserPermissions(httpSession, userUpdateRequest.getUserId());
        userService.updateUser(userUpdateRequest);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession httpSession) {
        final UserResponse loginUser = userService.login(userId, password);
        httpSession.setAttribute(SessionAttributeNames.LOGIN_USER_ID, loginUser.getUserId());
        httpSession.setAttribute(SessionAttributeNames.LOGIN_USER_NAME, loginUser.getName());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        if (httpSession != null) {
            httpSession.invalidate();
        }
        return "redirect:/";
    }

    private void checkUserPermissions(HttpSession httpSession, String userId) {
        if (!httpSession.getAttribute(SessionAttributeNames.LOGIN_USER_ID).equals(userId)) {
            throw new AccessDeniedException();
        }
    }
}
