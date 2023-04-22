package kr.codesquad.cafe.config;

import kr.codesquad.cafe.post.PostIdToPostConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final HandlerInterceptor authInterceptor;
    private final PostIdToPostConverter postIdToPostConverter;

    public WebConfig(HandlerInterceptor authInterceptor, PostIdToPostConverter postIdToPostConverter) {
        this.authInterceptor = authInterceptor;
        this.postIdToPostConverter = postIdToPostConverter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/users/**", "/posts/**")
                .excludePathPatterns("/users/login", "/users/joinForm");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(postIdToPostConverter);
    }
}
