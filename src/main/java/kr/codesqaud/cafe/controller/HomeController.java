package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/index"})
    public String home() {
        return "/index";
    }

    @GetMapping("user/login")
    public String loginForm() {
        return "/user/login";
    }

    @GetMapping("user/form")
    public String joinForm() {
        return "/user/form";
    }

//    @GetMapping("user/list")
//    public String listUsers(Model model) {
////        model.addAttribute("user",)
//        return "/user/list";
//    }

    @GetMapping("user/profile")
    public String profile() {
        return "/user/profile";
    }

    @GetMapping("qna/form")
    public String makeQna() {
        return "/qna/form";
    }

    @GetMapping("qna/show")
    public String showQna() {
        return "/qna/show";
    }

    //todo : 로그인 실패 화면 띄우기
//    @GetMapping("")
//    public String loginFailed() {
//        return "/user/login_failed";
//    }
}
