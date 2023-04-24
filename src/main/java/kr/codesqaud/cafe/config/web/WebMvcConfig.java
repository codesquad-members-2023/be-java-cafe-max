package kr.codesqaud.cafe.config.web;

import kr.codesqaud.cafe.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            .order(Ordered.HIGHEST_PRECEDENCE)
            .addPathPatterns("/qna/**", "/users/**")
            .excludePathPatterns("/users");
    }
}
