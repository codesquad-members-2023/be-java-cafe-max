package kr.codesqaud.cafe.config.web;

import kr.codesqaud.cafe.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            .order(Ordered.HIGHEST_PRECEDENCE)
            .addPathPatterns("/qna/**", "/users/**")
            .excludePathPatterns("/users", "/users/new");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
            .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/fonts/**")
            .addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/images/**")
            .addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/js/**")
            .addResourceLocations("classpath:/static/js/");
    }
}
