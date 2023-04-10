package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.controller.request.UserRegisterRequest;
import kr.codesqaud.cafe.user.service.UserService;
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

    @PostMapping("")
    public String register(UserRegisterRequest user) {
        userService.register(user);
        return "redirect:users";
    }

    @GetMapping("")
    public String showUserList(Model model) {
        userService.showUserList(model);
        return "users/list";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") String id, Model model) {
        userService.showProfile(id, model);
        return "users/profile";
    }
}
