package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/form")
    public String register() {
        return "redirect:/users";
    }

    @GetMapping("/list")
    public String showUserList() {
        return "user/list";
    }

    @GetMapping("/{userId}")
    public void showProfile(@PathVariable("userId") String userId) {

    }
}
