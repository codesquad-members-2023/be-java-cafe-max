package kr.codesqaud.cafe.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		HttpSession httpSession = request.getSession();
		Object value = httpSession.getAttribute("sessionUser");
		if (value == null) {
			String urlPrior = request.getRequestURI().toString() + "?" + request.getQueryString();
			request.getSession().setAttribute("url_prior_login", urlPrior);

			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		return true;
	}
}
