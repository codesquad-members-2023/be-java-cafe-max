package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.controller.domain.User;
import kr.codesqaud.cafe.user.controller.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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

}
