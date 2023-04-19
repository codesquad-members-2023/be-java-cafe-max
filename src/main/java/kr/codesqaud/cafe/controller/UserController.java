package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.UserDto;
import kr.codesqaud.cafe.dto.UserForm;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<UserDto> userDtos = userService.findAll();
        model.addAttribute("userDtos", userDtos);

        return "user/list";
    }

    @GetMapping("/signup")
    public String showSignupForm(UserForm userForm) {
        return "user/form";
    }

    @PostMapping("/signup")
    public String signup(UserForm userForm) {
        userService.saveUser(userForm);

        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String findByUserId(@PathVariable("userId") String userId, Model model) {
        UserDto userDto = userService.findByUserId(userId);
        model.addAttribute("userDto", userDto);

        return "user/profile";
    }
}
