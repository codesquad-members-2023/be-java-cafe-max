package kr.codesqaud.cafe.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService = new UserService();

    @GetMapping("/signup")
    public String showSignup(UserForm userForm) {
        return "user/form";
    }

    @PostMapping("/signup")
    public String signup(UserForm userForm) {
        userService.saveUser(userForm);

        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<User> users = userService.getUserList();
        model.addAttribute("users", users);

        return "user/list";
    }

}
