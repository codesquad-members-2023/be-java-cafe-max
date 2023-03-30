package kr.codesqaud.cafe.controller;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class FormController {

    @GetMapping("/users")
    public String home(){
        return "createUser";
    }
}
