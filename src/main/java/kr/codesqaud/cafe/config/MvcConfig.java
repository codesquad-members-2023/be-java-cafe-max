package kr.codesqaud.cafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/user/form").setViewName("/user/form");
		registry.addViewController("/user/login").setViewName("/user/login");
		registry.addViewController("/").setViewName("/articles");
		registry.addViewController("/questions/form").setViewName("/qna/form");
	}
}
