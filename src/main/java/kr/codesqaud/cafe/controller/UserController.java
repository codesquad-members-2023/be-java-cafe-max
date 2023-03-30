package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UpdateFormDto;
import kr.codesqaud.cafe.domain.member.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/create")
    public String postSignUp(SignUpFormDto signUpFormDto){
        userService.signUp(signUpFormDto);
        return "redirect:/user";
    }

    @GetMapping("")
    public String getUserList(Model model){
        List<User> list =  userService.getList();
        model.addAttribute("memberList",list);
        return "/user/list";
    }

    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable("id")int id,Model model){
        List<User> list =  userService.getList();
        model.addAttribute("name",list.get(id).getName());
        model.addAttribute("email",list.get(id).getEmail());
        return "/user/profile";
    }

    @GetMapping("/update/{index}")
    public String getUpdateForm(@PathVariable("index")int index, Model model){
        List<User> list =  userService.getList();
        model.addAttribute("user",list.get(index));
        model.addAttribute("index",index);
        return "/user/update_form";
    }

    @PutMapping("/update/{index}")
    public String putUpdate(@PathVariable("index")int index, UpdateFormDto updateFormDto){
        userService.update(index,updateFormDto);
        return "redirect:/user";
    }


}


