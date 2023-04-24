package kr.codesqaud.cafe.global.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class SignInInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute(Session.LOGIN_USER) == null) {
			response.sendRedirect("/users/sign-in");
			return false;
		}
		return true;
	}
}
