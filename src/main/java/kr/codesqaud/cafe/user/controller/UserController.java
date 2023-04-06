package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.dto.UserAddForm;
import kr.codesqaud.cafe.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String addUser(@ModelAttribute UserAddForm userAddForm) {
        String userId = userService.add(userAddForm);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.getUser(userId));
        return "user/profile";
    }

}
