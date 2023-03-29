package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/form")
    public String signUp(UserForm userform) {
        User user = new User();
        user.setEmail(userform.getEmail());
        user.setNickname(userform.getNickname());
        user.setPassword(userform.getPassword());

        userService.join(user);

        return "redirect:/users";
    }
}
