package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    UserRepository userRepository;


    @GetMapping("/create")
    public String create() {
        return "index";
    }

    @PostMapping("/create")
    public String register(User user) {
//        System.out.println("user : " + user);
        userRepository.save(user);
        return "redirect:/list";
    }

//    @GetMapping("/list")
//    public String list(Model model) {
//        model.addAttribute("users", users);
//        return "list";
//    }
}
