package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class MemberController {
    @GetMapping("/signup")
    public String getSignUpPage(){
        return "/user/form";
    }
}
