package kr.codesqaud.cafe.common.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/",
                        "/articles",
                        "/users/login",
                        "/users/join",
                        "/users"
                )
                .addPathPatterns(
                        "/articles/**",
                        "/users/**"
                );
    }
}
