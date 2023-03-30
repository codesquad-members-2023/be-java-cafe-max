package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.UserDto;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class UserController {

    UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @PostMapping("/user/join")
    public String join(UserDto userDto) {
        userService.addUser(userDto);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showUserProfile(@PathVariable int userId, Model model) {
        model.addAttribute("user", userService.getUserByUserId(userId));
        return "user/profile";
    }

}
