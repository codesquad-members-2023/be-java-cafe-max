package kr.codesquad.cafe.global.auth;

import kr.codesquad.cafe.global.exception.UnauthorizedAccessException;
import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.user.domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Objects;

import static org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes;

@Aspect
@Component
public class AuthBeforeAdvice {

    public static final String USER = "user";

    private static User getUser() {
        HttpSession session = getSession();
        return (User) session.getAttribute(USER);
    }

    private static HttpSession getSession() {
        return ((ServletRequestAttributes) currentRequestAttributes()).getRequest().getSession();
    }

    private static Post getPost(JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs()).filter(Post.class::isInstance)
                .map(Post.class::cast)
                .findFirst()
                .orElseThrow(UnauthorizedAccessException::new);
    }

    private static Long getPathId(JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs()).filter(Long.class::isInstance)
                .map(Long.class::cast)
                .findFirst()
                .orElseThrow();
    }

    @Pointcut("@annotation(kr.codesquad.cafe.user.annotation.ValidUserIdPath)")
    public void validUserIdPath() {
    }

    @Before("validUserIdPath()")
    public void validateUserIdPath(JoinPoint joinPoint) {
        User user = getUser();
        Long userId = getPathId(joinPoint);
        if (!user.isSameId(userId)) {
            throw new UnauthorizedAccessException();
        }
    }

    @Pointcut("@annotation(kr.codesquad.cafe.post.annotation.ValidPostIdPath)")
    public void validPostIdPath() {
    }

    @Before("validPostIdPath()")
    public void validatePathId(JoinPoint joinPoint) {
        User user = getUser();
        Post post = getPost(joinPoint);
        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new UnauthorizedAccessException();
        }
    }
}
