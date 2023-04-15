package kr.codesqaud.cafe.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kr.codesqaud.cafe.exception.common.Unauthorized;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.config.session.SignIn;
import kr.codesqaud.cafe.util.SignInSessionUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SignInArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SignIn.class) &&
            parameter.getParameterType().isAssignableFrom(AccountSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new Unauthorized();
        }

        return session.getAttribute(SignInSessionUtil.SIGN_IN_SESSION_NAME);
    }
}
