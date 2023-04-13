package kr.codesqaud.cafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SignInInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/members/sign-up", "/members/sign-in", "/members/sign-out",
                "/css/**", "/error/**");
    }
}
