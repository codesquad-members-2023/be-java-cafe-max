package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("user/login")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("user/form")
    public String joinForm() {
        return "user/form";
    }

    @GetMapping("qna/form")
    public String makeQna() {
        return "qna/form";
    }
}
