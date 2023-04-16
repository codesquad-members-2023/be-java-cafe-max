package kr.codesqaud.cafe.global.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.codesqaud.cafe.global.common.constant.SessionAttributeNames;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		HttpSession httpSession = request.getSession(false);

		if (httpSession == null || httpSession.getAttribute(SessionAttributeNames.LOGIN_USER_ID) == null) {
			response.sendRedirect("/login");
			return false;
		}

		return true;
	}
}
