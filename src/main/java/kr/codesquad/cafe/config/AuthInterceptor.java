package kr.codesquad.cafe.config;

import kr.codesquad.cafe.user.User;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (request.getRequestURI().equals("/users")) {
            if (request.getMethod().equals("POST")) {
                return true;
            } else if (request.getMethod().equals("GET") && !user.isManager()) {
                throw new RuntimeException();
            }
        }
        if (user == null) {
            response.sendRedirect("/users/login");
            return false;
        }
        return true;
    }
}
