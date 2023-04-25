package kr.codesqaud.cafe.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.codesqaud.cafe.exception.comment.ApiUnauthorizedException;
import kr.codesqaud.cafe.util.SignInSessionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SignInInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SignInSessionUtil.SIGN_IN_SESSION_NAME) == null) {
            handleRedirectOrThrowException(request, response);
            return false;
        }

        return true;
    }

    private void handleRedirectOrThrowException(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        if (request.getRequestURI().contains("/api")) {
            throw new ApiUnauthorizedException();
        }

        response.sendRedirect("/members/sign-in");
    }
}
