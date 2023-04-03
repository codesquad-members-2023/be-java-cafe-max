package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.UserForm;
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
    private final UserService userService = new UserService();

    @GetMapping
    public String showList(Model model) {
        List<User> users = userService.getUserList();
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/signup")
    public String showSignup(UserForm userForm) {
        return "user/form";
    }

    @PostMapping("/signup")
    public String signup(UserForm userForm) {
        userService.saveUser(userForm);

        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String findByUserId(@PathVariable("userId") String userId, Model model) {
        User user = userService.findByUserId(userId);
        model.addAttribute("user", user);

        return "user/profile";
    }
}
