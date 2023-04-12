package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.LoginForm;
import kr.codesqaud.cafe.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login(LoginForm loginForm) {
        return "user/login";
    }
}
