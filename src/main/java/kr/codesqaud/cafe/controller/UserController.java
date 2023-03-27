package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.member.UserLog;
import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UserDto;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
        UserDto userDto = new UserDto(userId,password,name,email);
        userService.signUp(userDto);
        return "redirect:/user";
    }

    @GetMapping("")
    public String getUserList(Model model){
        List<UserDto> list =  userService.getList();
        model.addAttribute("memberList",list);
        return "/user/list";
    }

    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable("id")int id,Model model){
        List<UserDto> list =  userService.getList();
        model.addAttribute("name",list.get(id).getName());
        model.addAttribute("email",list.get(id).getEmail());
        return "/user/profile";
    }


}


