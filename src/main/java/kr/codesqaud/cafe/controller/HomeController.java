package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/index","/index.html"})
    public String home() {
        return "index";
    }

    @GetMapping("user/login.html")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("user/form.html")
    public String joinForm() {
        return "user/form";
    }

    @GetMapping("user/list.html")
    public String listUser() {
        return "user/list";
    }

    @GetMapping("user/profile.html")
    public String profile() {
        return "user/profile";
    }

    @GetMapping("qna/form.html")
    public String makeQna() {
        return "qna/form";
    }

    @GetMapping("qna/show.html")
    public String showQna() {
        return "qna/show";
    }
}
