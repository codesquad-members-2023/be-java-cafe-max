package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user/create")
    public String signUpPage() {
        return "/user/form";
    }

    @GetMapping("/user/list")
    public String listPage() {
        return "/user/list";
    }
}
