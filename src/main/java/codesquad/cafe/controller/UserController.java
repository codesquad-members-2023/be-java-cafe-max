package codesquad.cafe.controller;

import codesquad.cafe.domain.User;
import codesquad.cafe.dto.UserRequestDto;
import codesquad.cafe.service.UserService;
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

    @GetMapping("/join")
    public String showJoinForm() {
        return "user/form";
    }

    @PostMapping("")
    public String registerUser(UserRequestDto userRequestDto) {
        userService.join(userRequestDto.toEntity());
        return "redirect:/users";
    }

    @GetMapping("")
    public String showUsers(Model model) {
        List<User> users = userService.showUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable("userId") String id, Model model) {
        User user = userService.findUser(id);
        model.addAttribute("user", user);
        return "user/profile";
    }
}
