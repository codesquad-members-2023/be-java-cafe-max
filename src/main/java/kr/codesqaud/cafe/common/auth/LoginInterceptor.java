package kr.codesqaud.cafe.common.auth;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    private static final String LOGIN_URL = "/users/login";
    private static final String USER_JOIN_URL = "/users";
    private static final String ARTICLE_CREATE_URL = "/articles";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.POST.matches(request.getMethod())) {
            final String requestURI = request.getRequestURI();
            if (USER_JOIN_URL.equals(requestURI) || ARTICLE_CREATE_URL.equals(requestURI)) {
                return true;
            }
        }

        final HttpSession session = request.getSession(false);

        if (hasLoginSession(session)) {
            return true;
        }

        response.sendRedirect(LOGIN_URL);

        return false;
    }

    private boolean hasLoginSession(HttpSession session) {
        return session != null && session.getAttribute("loginUser") != null;
    }
}
