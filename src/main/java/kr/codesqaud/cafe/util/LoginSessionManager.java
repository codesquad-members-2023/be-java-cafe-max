package kr.codesqaud.cafe.util;

import kr.codesqaud.cafe.controller.dto.login.LoggedInDTO;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class LoginSessionManager {
    private final HttpSession session;
    public static final String LOGIN_USER = "loginUser";

    public LoginSessionManager(HttpSession session) {
        this.session = session;
    }

    public void save(LoggedInDTO loginUser) {
        session.setAttribute(LOGIN_USER, loginUser);
    }

    public LoggedInDTO getLoginUser() {
        return (LoggedInDTO) session.getAttribute(LOGIN_USER);
    }

    public void remove() {
        session.invalidate();
    }

    public void updateInfo(LoggedInDTO updatedLoginUser) {
        save(updatedLoginUser);
    }

    public void throwErrorIfAnonymous() {
        if(getLoginUser() == null) throw new UnauthorizedException();
    }
}
