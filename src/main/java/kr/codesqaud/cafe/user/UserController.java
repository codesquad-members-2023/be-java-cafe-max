package kr.codesqaud.cafe.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        return "redirect:/user/signup";
    }

}
