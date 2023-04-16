package kr.codesqaud.cafe.common.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws IOException {
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("sessionedUser") == null) {
			response.sendRedirect("/user/login");
			return false;
		}
		return true;
	}
}
