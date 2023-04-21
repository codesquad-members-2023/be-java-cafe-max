package kr.codesqaud.cafe.common.auth;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {
    private static final String LOGIN_URL = "/users/login";
    private static final String USER_JOIN_URL = "/users";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (hasLoginSession(request.getSession(false)) || isJoinUserRequest(request)) {
            return true;
        }

        response.sendRedirect(LOGIN_URL);

        return false;
    }

    private boolean isJoinUserRequest(HttpServletRequest request) {
        return HttpMethod.POST.matches(request.getMethod()) && USER_JOIN_URL.equals(request.getRequestURI());
    }

    private boolean hasLoginSession(HttpSession session) {
        return session != null && session.getAttribute("loginUser") != null;
    }
}
