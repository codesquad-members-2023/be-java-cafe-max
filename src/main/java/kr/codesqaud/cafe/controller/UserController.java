package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.UserRequest;
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

    @PostMapping("/register")
    public String register(UserRequest user) {
        userService.register(user);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String showUserList(Model model) {
        userService.showUserList(model);
        return "users/list";
    }

    @GetMapping("/{userId}")
    public void showProfile(@PathVariable("userId") long userId) {

    }
}
