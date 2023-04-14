package kr.codesqaud.cafe.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.user.User;
import kr.codesqaud.cafe.web.SessionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(
            @Valid LoginFormDTO loginForm, BindingResult bindingResult, HttpServletRequest request) {
        User loginUser = loginService.login(loginForm);

        if (bindingResult.hasErrors()) {
            logger.info("로그인 실패");
            return "user/login_failed";
        }

        if (loginUser == null) {
            logger.info("로그인 실패");
            return "user/login_failed";
        }
        logger.info("로그인 성공");

        HttpSession session = request.getSession();
        session.setAttribute(SessionConstant.LOGIN_USER_ID, loginUser.getUserId());
        session.setAttribute(SessionConstant.LOGIN_USER_NAME, loginUser.getName());

        return "redirect:/";
    }

    @GetMapping("/logout") // TODO: Post로 리팩터링 필요
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
