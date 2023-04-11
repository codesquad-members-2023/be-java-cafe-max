package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.dto.UserAddForm;
import kr.codesqaud.cafe.user.dto.UserLoginForm;
import kr.codesqaud.cafe.user.dto.UserResponse;
import kr.codesqaud.cafe.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String addUser(@ModelAttribute UserAddForm userAddForm) {
        String userId = userService.addUser(userAddForm);
        return "redirect:/user/list";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserLoginForm userLoginForm, HttpSession session) {
        Optional<UserResponse> loginResult = userService.loginCheck(userLoginForm);
        if (!loginResult.isPresent()) {
            return "user/login_failed";
        }
        session.setAttribute("sessionUser", loginResult.get());
        return "redirect:/board/list";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.removeAttribute("sessionUser");
        return "user/login";
    }

    @GetMapping("/list")
    public String getUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.getUser(userId));
        return "user/profile";
    }

}
