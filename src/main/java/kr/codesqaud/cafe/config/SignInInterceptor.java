package kr.codesqaud.cafe.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.codesqaud.cafe.util.SignInSessionUtil;
import org.springframework.web.servlet.HandlerInterceptor;

public class SignInInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SignInSessionUtil.SIGN_IN_SESSION_NAME) == null) {
            response.sendRedirect("/members/sign-in");
            return false;
        }

        return true;
    }
}
