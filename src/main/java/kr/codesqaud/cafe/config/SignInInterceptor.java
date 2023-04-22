package kr.codesqaud.cafe.config;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.util.SignInSessionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SignInInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SignInSessionUtil.SIGN_IN_SESSION_NAME) == null) {
            response.sendRedirect("/members/sign-in");
            return false;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Arrays.stream(handlerMethod.getMethodParameters())
            .filter(parameter -> parameter.getParameterType().isAssignableFrom(AccountSession.class))
            .findFirst()
            .ifPresent(methodParameter ->
                request.setAttribute("accountSession", session.getAttribute(SignInSessionUtil.SIGN_IN_SESSION_NAME)));

        return true;
    }
}
