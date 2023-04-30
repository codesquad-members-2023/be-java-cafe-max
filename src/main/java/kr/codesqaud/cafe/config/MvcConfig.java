package kr.codesqaud.cafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final SignInInterceptor signInInterceptor;

    public MvcConfig(SignInInterceptor signInInterceptor) {
        this.signInInterceptor = signInInterceptor;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/","/posts");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/posts", "/members/sign-up", "/sign-in", "/sign-out",
                "/css/**", "/error/**", "/js/**");
    }
}
