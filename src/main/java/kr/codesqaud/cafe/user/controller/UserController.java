package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/add")
    public String addUser() {
        return "user/form";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userRepository.add(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.getUserList());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.getUser(userId));
        return "user/profile";
    }

}
