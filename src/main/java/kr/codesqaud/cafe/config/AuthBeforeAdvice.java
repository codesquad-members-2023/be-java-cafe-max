package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.account.User;
import kr.codesqaud.cafe.global.IllegalAccessIdException;
import kr.codesqaud.cafe.post.Post;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
public class AuthBeforeAdvice {

    @Pointcut("@annotation(kr.codesqaud.cafe.account.annotation.ValidUserIdPath)")
    public void validUserIdPath() {
    }

    @Before("validUserIdPath()")
    public void validateUserIdPath(JoinPoint joinPoint) {
        User user = getUser(joinPoint);
        Long userId = getPathId(joinPoint);
        if (!user.isSameId(userId)) {
            throw new IllegalAccessIdException();
        }
    }

    @Pointcut("@annotation(kr.codesqaud.cafe.post.annotation.ValidPostIdPath)")
    public void validPostIdPath() {
    }

    @Before("validPostIdPath()")
    public void validatePathId(JoinPoint joinPoint) {
        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        User user = (User) session.getAttribute("user");
        Post post = getPost(joinPoint);
        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new IllegalAccessIdException();
        }
    }

    private static Post getPost(JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs()).filter(Post.class::isInstance)
                .map(Post.class::cast)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private static Long getPathId(JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs()).filter(Long.class::isInstance)
                .map(Long.class::cast)
                .findFirst()
                .orElseThrow();
    }

    private static User getUser(JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs()).filter(User.class::isInstance)
                .map(User.class::cast)
                .findFirst()
                .orElseThrow();
    }
}
