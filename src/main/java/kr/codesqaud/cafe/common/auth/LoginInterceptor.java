package kr.codesqaud.cafe.common.auth;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    private static final String LOGIN_URL = "/users/login";
    private static final String POST_METHOD = "POST";
    private static final String USER_JOIN_URL = "/users";
    private static final String ARTICLE_CREATE_URL = "/articles";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (POST_METHOD.equals(request.getMethod())) {
            final String requestURI = request.getRequestURI();
            if (USER_JOIN_URL.equals(requestURI) || ARTICLE_CREATE_URL.equals(requestURI)) {
                return true;
            }
        }

        final HttpSession session = request.getSession(false);

        if (hasNotLoginSession(session)) {
            response.sendRedirect(LOGIN_URL);
            return false;
        }

        return true;
    }

    private boolean hasNotLoginSession(HttpSession session) {
        return session == null || session.getAttribute("loginUser") == null;
    }
}
