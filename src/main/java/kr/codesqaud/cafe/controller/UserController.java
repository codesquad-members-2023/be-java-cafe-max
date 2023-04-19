package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
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

    @GetMapping("/join")
    public String getUserForm() {
        return "/user/form";
    }

    @PostMapping("/users")
    public String joinUser(User user) {
        userService.join(user);
        return "redirect:/users";
    }
    @GetMapping("/users")
    public String getUserList(Model model) {
        model.addAttribute("users",  userService.getUserList());
        return "/user/list";
    }
    @GetMapping("/users/{userId}")
    public String userProfile(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", userService.getUserByUserId(userId));
        return "user/profile";
    }
}
