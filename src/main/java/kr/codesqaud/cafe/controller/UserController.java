package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserJoinForm;
import kr.codesqaud.cafe.dto.UserLoginForm;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user/form")
    public String joinForm() {
        return "user/form";
    }

    @PostMapping("user/create")
    public String create(UserJoinForm form) {
        User user = new User(form.getUserId(), form.getPassword(), form.getName(), form.getEmail());

        boolean isSignUpSuccess = userService.join(user);

        if (isSignUpSuccess) {
            return "redirect:/users";
        }
        return "redirect:/user/form";

    }

    @GetMapping("users")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("users/{userId}")
    public String profile(Model model, @PathVariable String userId) {
        model.addAttribute("user", userService.findOne(userId));
        return "user/profile";
    }

    @GetMapping("user/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("user/login")
    public String login(UserLoginForm form) {
        boolean isLoginSuccess = userService.login(form.getUserId(), form.getPassword());
        if (isLoginSuccess) {
            return "redirect:/";
        }
        return "redirect:/user/login";
    }
}
