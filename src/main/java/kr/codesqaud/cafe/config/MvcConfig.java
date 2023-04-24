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

        //로직이 없는 매핑이기 때문에 미션에서 추천해준 방식대로 매핑을 적용해 보았습니다.
        registry.addViewController("/user/sign-up-form").setViewName("user/sign-up-form");
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/articles/forbidden").setViewName("articles/forbidden");

    }
}
