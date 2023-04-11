package kr.codesqaud.cafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        //article
        registry.addViewController("/article").setViewName("post/form");

        //user
        registry.addViewController("/user/sign-up-form").setViewName("user/form");
        registry.addViewController("/user/sign-in").setViewName("user/login");
        registry.addViewController("/user/sign-in-success").setViewName("user/login_success");

        //error
        registry.addViewController("/error-page").setViewName("error/error");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**");
    }
}
