package kr.codesqaud.cafe.util;

//import com.mysql.cj.Session;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckFilter implements Filter {
    // 로그인 하지 않은 사용자에게 보여 줄 페이지
    private static final String[] whitelist = {"/","/users/create", "/login", "/logout", "/css/*", "/fonts/*", "/images/*" };

    // init(), destroy() 는 default 키워드 덕분에 구현 할 필요가 없어진 인터페이스의 method
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 인증 체크 하기
        if (isLoginCheckPath(requestURI)) {
            HttpSession session = httpRequest.getSession(false);
            // 세션이 없거나 로그인한 사용자가 없으면
            if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
                // 권한이 없으면 로그인 페이지를 넘겨주기 -> 로그인 하면 로그인 하기 전의 페이지를 다시 반환해주기.
                httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                return;
            }
        }

        // 가장 중요. 이 친구가 없으면 다음 단계로 진행하지 못한다.
        // 다음 필터를 호출한다. 다음 필터가 없으면 서블릿을 호출한다.
        chain.doFilter(request, response);
    }

    // 화이트 리스트인 경우 인증 체크X
    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
