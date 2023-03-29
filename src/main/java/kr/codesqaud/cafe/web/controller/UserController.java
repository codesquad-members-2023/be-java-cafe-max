package kr.codesqaud.cafe.web.controller;

import kr.codesqaud.cafe.web.dto.UserResponseDto;
import kr.codesqaud.cafe.web.dto.UserSavedRequestDto;
import kr.codesqaud.cafe.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/form")
    public String form() {
        return "user/form";
    }

    @PostMapping("/user/create")
    public String newUser(UserSavedRequestDto requestDto, Model model) {
        UserResponseDto userResponseDto = userService.save(requestDto);
        model.addAttribute("user", userResponseDto);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }
}
