package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        List<kr.codesqaud.cafe.user.domain.User> users = userService.findUsers();
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

    @GetMapping("/user/{id}")
    public String profile(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/profile";
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/form")
    public String create(UserCreateRequest userCreateRequest) {
        User user = new User.Builder()
                .id(userCreateRequest.getUserId())
                .name(userCreateRequest.getName())
                .email(userCreateRequest.getEmail())
                .password(userCreateRequest.getPassword())
                .build();
        userService.join(user);
        return "redirect:/";
    }
}

