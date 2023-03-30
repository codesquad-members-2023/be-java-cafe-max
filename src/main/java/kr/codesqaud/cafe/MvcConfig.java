package kr.codesqaud.cafe;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        //user
        registry.addViewController("/user/form").setViewName("/user/form");
        registry.addViewController("/user/login").setViewName("/user/login");
        registry.addViewController("/user/login_success").setViewName("/user/login_success");

        //post
        registry.addViewController("/post/form").setViewName("/post/form");
    }
}
