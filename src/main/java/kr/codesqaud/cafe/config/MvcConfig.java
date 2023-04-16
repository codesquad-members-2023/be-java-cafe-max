package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 우선 순위를 가장 높게
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/user/form").setViewName("user/form");
        registry.addViewController("/user/loginForm").setViewName("user/login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/user/**", "/board/**")
                .excludePathPatterns("/user/loginForm", "/user/login", "/user/form", "/user", "/board/list",
                        "/css/**", "/fonts/**", "/images/**", "/js/**", "/*.ico");
    }
}
