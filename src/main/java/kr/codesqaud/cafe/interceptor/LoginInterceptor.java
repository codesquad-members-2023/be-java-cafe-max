package kr.codesqaud.cafe.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("sessionedUser") == null) {
			response.sendRedirect("/users/login");
			return false;
		}
		return true;
	}
}
