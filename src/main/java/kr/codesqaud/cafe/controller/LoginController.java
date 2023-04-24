package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.login.AuthenticationDTO;
import kr.codesqaud.cafe.controller.dto.login.LoggedInDTO;
import kr.codesqaud.cafe.service.LoginService;
import kr.codesqaud.cafe.util.LoginSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final LoginSessionManager loginSessionManager;

    public LoginController(LoginService loginService, LoginSessionManager loginSessionManager) {
        this.loginService = loginService;
        this.loginSessionManager = loginSessionManager;
    }

    //todo : MvcConfigure 활성화 하려면 get, post url 달라야 함. 변경하기
    @PostMapping("/login-try")
    public String login(@ModelAttribute final AuthenticationDTO authenticationDTO, final Model model) {
        boolean isLoginFailed = !loginService.checkLoginUser(authenticationDTO);
        if (isLoginFailed) {
            model.addAttribute("loginFailed", true);
            return "login/form";
        }

        LoggedInDTO loginUser = loginService.findByUserId(authenticationDTO.getUserId());

        //세션 객체 얻어오기
        loginSessionManager.save(loginUser);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout() {
        loginSessionManager.remove();
        return "redirect:/";
    }

}
