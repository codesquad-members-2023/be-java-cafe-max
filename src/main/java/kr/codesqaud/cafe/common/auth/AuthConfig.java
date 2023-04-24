package kr.codesqaud.cafe.common.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns(
                        "/articles/create-form",
                        "/articles/update-form",
                        "/users/**"
                )
                .excludePathPatterns(
                        "/users/login",
                        "/users/join-form");
    }
}
