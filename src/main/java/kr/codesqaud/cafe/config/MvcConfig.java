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
		registry.addViewController("/user/sign-in-form").setViewName("user/login");
		registry.addViewController("/user/sign-in-success").setViewName("user/login_success");
		registry.addViewController("/user/profile/{id}/form").setViewName("user/updateForm");

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/")
			.excludePathPatterns("/user/list")
			.excludePathPatterns("/user/sign-up")
			.excludePathPatterns("/user/sign-up-form")
			.excludePathPatterns("/user/sign-in-form")
			.excludePathPatterns("/user/sign-in")
			.excludePathPatterns("/user/sign-in/{id}")
			.excludePathPatterns("/css/**", "/images/**", "/js/**");

	}
}
