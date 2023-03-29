package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(User user) {
        userService.register(user);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String showUserList() {
        return "users/list";
    }

    @GetMapping("/{userId}")
    public void showProfile(@PathVariable("userId") String userId) {

    }
}
