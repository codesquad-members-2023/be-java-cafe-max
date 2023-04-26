package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.request.JoinRequest;
import kr.codesqaud.cafe.controller.dto.request.LoginRequest;
import kr.codesqaud.cafe.controller.dto.request.ProfileEditRequest;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create-user-form")
    public String showJoinForm() {
        return "user/form";
    }

    @GetMapping("/login-user-form")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute JoinRequest joinRequest) {
        userService.join(joinRequest);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String listAllUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showProfilePage(@PathVariable final String userId, final Model model) {
        model.addAttribute("user", userService.findByUserId(userId));
        return "user/profile";
    }

    @GetMapping("/profile-edit-form/{userId}")
    public String showProfileEditPage(@PathVariable final String userId, final Model model) {
        model.addAttribute("userId", userId);
        return "user/edit_form";
    }

    @PutMapping("/users/{userId}")
    public String editUserProfile(@PathVariable final String userId, @ModelAttribute final ProfileEditRequest request) {
        userService.editUserProfile(userId, request);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginRequest loginRequest, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "user/login";
        }
        User loginUser = userService.login(loginRequest.getUserId(), loginRequest.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/login_failed";
        }
        HttpSession session = request.getSession(true);

        session.setAttribute("loginUser", loginUser);
        session.setAttribute("userId", loginUser.getUserId());
        session.setAttribute("userName", loginUser.getUserName());

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
