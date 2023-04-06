package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/user/form")
    public String form() {
        return "user/form";
    }

    @GetMapping("/user/list")
    public String list(Model model) {
        List<User> users = userService.fineUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/user/login_failed")
    public String loginFailed() {
        return "user/login_failed";
    }

    @GetMapping("/user/profile")
    public String profile() {
        return "user/profile";
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/form")
    public String create(UserForm userForm) {
        User user = new User.Builder()
                .id(userForm.getUserId())
                .name(userForm.getName())
                .email(userForm.getEmail())
                .password(userForm.getPassword())
                .build();
        userService.join(user);
        return "redirect:/";
    }
}
