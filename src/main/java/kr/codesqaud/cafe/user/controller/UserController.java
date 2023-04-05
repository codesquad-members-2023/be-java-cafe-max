package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.repository.UserJdbcRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserJdbcRepository userRepository;

    public UserController(UserJdbcRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public String addUser(@ModelAttribute User user) {
        String userId = userRepository.add(user);
        // 중복된 아이디가 존재하는 경우
        if (userId == null) {
            return "/user/form_failed";
        }
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.findByUserId(userId));
        return "user/profile";
    }

}
