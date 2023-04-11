package kr.codesqaud.cafe.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession(false);

        if (httpSession == null || httpSession.getAttribute(SessionAttributeNames.LOGIN_USER_ID) == null) {
            response.sendRedirect("/users/login");
            return false;
        }

        return true;
    }
}
