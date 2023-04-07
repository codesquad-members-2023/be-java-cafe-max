package kr.codesqaud.cafe;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //user
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/user/login-success").setViewName("user/login_success");
    }
}
