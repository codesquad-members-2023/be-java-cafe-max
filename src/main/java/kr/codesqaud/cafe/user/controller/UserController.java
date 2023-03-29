package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.controller.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    List<User> users = new ArrayList<>();

    @GetMapping("/add")
    public String addUser() {
        return "user/form";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        users.add(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", users);

        return "user/list";
    }
}
