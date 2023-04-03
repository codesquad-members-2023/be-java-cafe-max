package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user/form")
    public String loginPage() {
        return "user/form";
    }

    @PostMapping("user/form")
    public String register(User user) {
        userService.join(user);
        return "redirect:/users";
    }
    @GetMapping("users")
    public String getUserList(Model model) {
        List<User> users = userService.showAllUser();
        model.addAttribute("users", users);
        return "user/list";
    }


}
