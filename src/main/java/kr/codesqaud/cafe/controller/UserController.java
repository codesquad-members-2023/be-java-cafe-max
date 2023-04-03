package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UpdateFormDto;
import kr.codesqaud.cafe.domain.member.User;
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
    public String getProfile(@PathVariable int id, Model model) {
        List<User> list = userMemoryService.getList();
        model.addAttribute("name", list.get(id).getName());
        model.addAttribute("email", list.get(id).getEmail());
        return "/user/profile";
    }

    @GetMapping("/update/{index}")
    public String getUpdateForm(@PathVariable int index, Model model) {
        List<User> list = userMemoryService.getList();
        model.addAttribute("user", list.get(index));
        model.addAttribute("index", index);
        return "/user/update_form";
    }

    @PutMapping("/update/{index}")
    public String putUpdate(@PathVariable int index, UpdateFormDto updateFormDto) {
        userMemoryService.update(index, updateFormDto);
        return "redirect:/user";
    }


}


