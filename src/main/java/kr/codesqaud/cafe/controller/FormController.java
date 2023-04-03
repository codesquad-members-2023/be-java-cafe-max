package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {
    @GetMapping("/form")
    public String getUserForm(){
        return "form";
    }
    @PostMapping("/users/create")
    public String createUser(User user){
        System.out.println(user.toString());
        return "";
    }
}
