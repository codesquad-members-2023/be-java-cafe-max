package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.LoginDTO;
import kr.codesqaud.cafe.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    // todo : HttpServletResponse 뭔지 찾아보기
    @PostMapping("/login")
    public String login(final LoginDTO loginDTO, final Model model, HttpServletRequest request, HttpServletResponse response) {
        boolean isLoginFailed = !loginService.checkIdAndPw(loginDTO);
        if(isLoginFailed) {
            model.addAttribute("loginFailed", true);
            return "login/form";
        }
        LoginDTO loginUser = loginService.findId(loginDTO.getUserId());

        //세션 객체 얻어오기
        HttpSession session = request.getSession();
        //세션 객체에 id 저장
        session.setAttribute("loginUser", loginUser);

        //쿠키 생성. 시간 정보 설정 생략 시 세션 쿠키(브라우저 종료 시 삭제)
        Cookie idCookie = new Cookie("idCookie", String.valueOf(loginUser.getId()));
        response.addCookie(idCookie);

        return "redirect:/";

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false); // 현재 세션이 없으면 null 반환
        if (session != null) {
            session.invalidate(); // 현재 세션을 무효화
        }
        expireCookie(response, "id");
        return "redirect:/";
    }

    //todo : 로그아웃 해도 application - cookie 안 사라짐
    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
