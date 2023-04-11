package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.LoginDTO;
import kr.codesqaud.cafe.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login/form";
    }

    @PostMapping("/login")
    public String login(final LoginDTO loginDTO, final Model model) {
        boolean isLoginFailed = !loginService.authenticate(loginDTO);
        if(isLoginFailed) {
           model.addAttribute("loginFailed", true);
           return "login/form";
        }
//        loginService.signIn(loginDTO);
        return "redirect:/";
    }
}
