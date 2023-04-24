package codesquad.cafe.global.interceptor;

import codesquad.cafe.global.constant.SessionAttributes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {

    private final Log log = LogFactory.getLog(LoginCheckInterceptor.class);

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionAttributes.LOGIN_USER.getValue()) == null) {
            log.info("intercept url : " + request.getRequestURL());
            log.info("[미인증 사용자 요청]");
            response.sendRedirect("/users/login");
            return false;
        }
        return true;
    }
}
