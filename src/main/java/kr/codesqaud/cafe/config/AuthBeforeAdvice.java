package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.account.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AuthBeforeAdvice {

    @Pointcut("@annotation(kr.codesqaud.cafe.account.annotation.ValidPathId)")
    public void validPathId() {

    }

    @Before("validPathId()")
    public void validatePathId(JoinPoint joinPoint) {
        User user = Arrays.stream(joinPoint.getArgs()).filter(User.class::isInstance)
                .map(User.class::cast)
                .findFirst()
                .orElseThrow();
        Long userId = Arrays.stream(joinPoint.getArgs()).filter(Long.class::isInstance)
                .map(Long.class::cast)
                .findFirst()
                .orElseThrow();
        if (!user.isSameId(userId)) {
            throw new RuntimeException("접근 할 수 없습니다.");
        }
    }
}
