package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.request.LoginRequest;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login-user-form")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/login";
        }
        User loginUser = loginService.login(loginRequest.getUserId(), loginRequest.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/login_failed";
        }


        return "redirect:/";

    }

}
