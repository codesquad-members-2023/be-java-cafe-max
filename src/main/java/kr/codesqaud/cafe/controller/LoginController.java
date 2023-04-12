package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.service.LoginService;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
}
