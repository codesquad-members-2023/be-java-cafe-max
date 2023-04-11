package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.interceptor.CacheControlInterceptor;
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
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/user/join").setViewName("user/form");
        registry.addViewController("/user/list").setViewName("redirect:/users");
        registry.addViewController("/user/profile").setViewName("user/profile");

        registry.addViewController("/questions/add").setViewName("qna/form");
        registry.addViewController("/questions/show").setViewName("qna/show");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(Ordered.HIGHEST_PRECEDENCE)
                // 인터셉터 적용
                .addPathPatterns("/questions/post", "/users/*")
                // 인터셉터 제외 패턴 지정
                .excludePathPatterns("/users/form", "/css/**", "/*.ico", "/fonts/**", "/images/**", "/js/**");

        registry.addInterceptor(new CacheControlInterceptor())
                .addPathPatterns("/questions/*", "/")
                .excludePathPatterns("/css/**", "/*.ico", "/fonts/**", "/images/**", "/js/**");
    }
}