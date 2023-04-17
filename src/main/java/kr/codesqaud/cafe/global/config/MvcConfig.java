package kr.codesqaud.cafe.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		//article
		registry.addViewController("/article").setViewName("article/form");

		//user
		registry.addViewController("/users/sign-up-form").setViewName("user/form");
		registry.addViewController("/users/sign-in-form").setViewName("user/sign-in");
		registry.addViewController("/users/sign-in-success").setViewName("user/sign-in-success");
		registry.addViewController("/users/profile/{id}/form").setViewName("user/updateForm");

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/")
			.excludePathPatterns("/users/list")
			.excludePathPatterns("/users/sign-up-form")
			.excludePathPatterns("/users/sign-in-form")
			.excludePathPatterns("/users/sign-in")
			.excludePathPatterns("/users/sign-up")
			.excludePathPatterns("/users/profile/{id}")
			.excludePathPatterns("/css/**", "/images/**", "/js/**");

	}
}
