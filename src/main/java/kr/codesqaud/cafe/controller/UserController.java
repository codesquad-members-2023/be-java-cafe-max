package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.member.UserLog;
import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    UserService userService;
    @Autowired
    public UserController(UserService memberService) {
        this.userService = memberService;
    }

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "/user/form";
    }

    @PostMapping("/create")
    public String postSignUp(SignUpFormDto signUpFormDto){
        String userId = signUpFormDto.getUserId();
        String password = signUpFormDto.getPassword();
        String name = signUpFormDto.getName();
        String email = signUpFormDto.getEmail();
        UserLog userLog = new UserLog(userId,password,name,email);
        userService.save(userLog);
        return "redirect:/user";
    }

    @GetMapping("")
    public String getUserList(Model model){
        List<UserLog> list =  userService.findAll();
        model.addAttribute("memberList",list);
        return "/user/list";
    }


}


