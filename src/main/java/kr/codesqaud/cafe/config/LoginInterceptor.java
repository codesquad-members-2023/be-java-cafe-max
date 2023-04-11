package kr.codesqaud.cafe.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");

        if (loginUser != null && modelAndView != null) {
            modelAndView.addObject("loginUserId", loginUser);
        }
    }
}
