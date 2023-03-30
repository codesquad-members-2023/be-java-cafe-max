package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/index", "/index.html"})
    public String home() {
        return "index";
    }

    @GetMapping("user/login.html")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("user/form.html")
    public String createForm() {
        return "user/form";
    }

    @GetMapping({"user/list", "user/list.html"})
    public String userList() {
        return "user/list";
    }

    @GetMapping("user/profile.html")
    public String userProfile() {
        return "user/profile";
    }

    @GetMapping("qna/show.html")
    public String qnaShow() {
        return "qna/show";
    }

    @GetMapping("qna/form.html")
    public String qnaForm() {
        return "qna/form";
    }
}
