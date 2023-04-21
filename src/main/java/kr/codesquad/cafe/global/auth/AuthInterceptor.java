package kr.codesquad.cafe.global.auth;

import kr.codesquad.cafe.global.exception.IllegalAccessIdException;
import kr.codesquad.cafe.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static final String USER = "user";
    public static final String USERS_URL = "/users";
    public static final String USERS_LOGIN_URL = "/users/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        if (requestURI.equals(USERS_URL)) {
            if (method.equals(POST.name())) {
                return true;
            } else if (method.equals(GET.name())) {
                checkRole(user);
                return true;
            }
        }
        if (user == null) {
            response.sendRedirect(USERS_LOGIN_URL);
            return false;
        }
        return true;
    }

    private void checkRole(User user) {
        if (user == null || !user.isManager()) {
            throw new IllegalAccessIdException();
        }
    }
}
