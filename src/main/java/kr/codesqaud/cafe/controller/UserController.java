package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UpdateFormDto;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.service.jdbc.UserJdbcService;
import kr.codesqaud.cafe.service.memory.UserMemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    UserMemoryService userMemoryService;

    public UserController(UserMemoryService userMemoryService) {
        this.userMemoryService = userMemoryService;
    }

    UserJdbcService userJdbcService;

    @Autowired
    public UserController(UserJdbcService userJdbcService) {
        this.userJdbcService = userJdbcService;
    }


    @PostMapping("/create")
    public String postSignUp(SignUpFormDto signUpFormDto) {
        userJdbcService.signUp(signUpFormDto);
        return "redirect:/user";
    }

    @GetMapping("")
    public String getUserList(Model model) {
        List<User> list = userJdbcService.users();
        model.addAttribute("memberList", list);
        return "/user/list";
    }

    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable String id, Model model) {
        User user = userJdbcService.findById(id);
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        return "/user/profile";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable String id, Model model) {
        User user = userJdbcService.findById(id);
        model.addAttribute("user", user);
        return "/user/update_form";
    }

    @PutMapping("/update/{id}")
    public String putUpdate(@PathVariable String id, UpdateFormDto updateFormDto) {
        userJdbcService.update(userJdbcService.findById(id), updateFormDto);
        return "redirect:/user";
    }


}


