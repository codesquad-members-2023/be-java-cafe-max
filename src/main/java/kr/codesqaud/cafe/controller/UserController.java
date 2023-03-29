package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.UserRegisterRequest;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        return "users/register";
    }

    @PostMapping("/register")
    public String register(UserRegisterRequest user) {
        userService.register(user);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String showUserList(Model model) {
        userService.showUserList(model);
        return "users/list";
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable("id") long id, Model model) {
        userService.showProfile(id, model);
        return "users/profile";
    }
}
