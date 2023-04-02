package kr.codesqaud.cafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/user/join").setViewName("user/form");
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/user/list").setViewName("redirect:/users");
        registry.addViewController("/user/profile").setViewName("user/profile");

        registry.addViewController("/questions/add").setViewName("qna/form");
        registry.addViewController("/questions/show").setViewName("qna/show");
    }
}