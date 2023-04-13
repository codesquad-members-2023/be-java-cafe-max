package kr.codesqaud.cafe.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SignInArgumentResolver());
    }
}
