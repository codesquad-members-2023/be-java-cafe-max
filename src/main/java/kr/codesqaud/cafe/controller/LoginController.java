package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.LoginDTO;
import kr.codesqaud.cafe.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    //todo : MvcConfigure 활성화 하려면 get, post url 달라야 함. 변경하기
    @PostMapping("/login-try")
    public String login(@ModelAttribute final LoginDTO loginDTO, final Model model, HttpServletRequest request) {
        boolean isLoginFailed = !loginService.checkLoginUser(loginDTO);
        if (isLoginFailed) {
            model.addAttribute("loginFailed", true);
            return "login/form";
        }

        LoginDTO loginUser = loginService.findId(loginDTO.getUserId());

        //세션 객체 얻어오기
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", loginUser);

        return "redirect:/";
    }

    @PostMapping("/logout-try")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 현재 세션이 없으면 null 반환
        if (session != null) {
            session.invalidate(); // 현재 세션을 무효화
        }

        return "redirect:/";
    }

}
