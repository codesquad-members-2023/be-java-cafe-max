package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.user.UserRequestDto;
import kr.codesqaud.cafe.dto.user.UserResponseDto;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public String register(final UserRequestDto userRequestDto) {
        userService.join(userRequestDto);
        return "redirect:/users";
    }


    @GetMapping
    public String getUserList(final Model model) {
        List<UserResponseDto> users = userService.showAllUser();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{id}")
    public String readUserProfile(@PathVariable String id, final Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "user/profile";
    }

}
