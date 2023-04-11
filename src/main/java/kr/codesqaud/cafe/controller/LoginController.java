package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.LoginFormDTO;
import kr.codesqaud.cafe.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(
            @Valid LoginFormDTO loginForm, BindingResult bindingResult, Model model, HttpServletRequest request) {
        User loginUser = loginService.login(loginForm);

        if (bindingResult.hasErrors()) {
            return "user/login_failed";
        }

        if (loginUser == null) {
            return "user/login_failed";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", loginUser);
        return "redirect:/";
    }

    @GetMapping("/logout") // TODO: Post로 구현해야 할 것 같은데, post로 하면 405에러 발생함
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
