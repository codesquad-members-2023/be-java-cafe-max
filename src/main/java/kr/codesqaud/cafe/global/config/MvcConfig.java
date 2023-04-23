package kr.codesqaud.cafe.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.codesqaud.cafe.global.common.interceptor.LoginInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registry.addViewController("/user/form").setViewName("user/form");
		registry.addViewController("/user/login").setViewName("user/login");
		registry.addViewController("/post/form").setViewName("post/form");
		registry.addViewController("/users/updateForm").setViewName("user/updateForm");

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
			.order(Ordered.HIGHEST_PRECEDENCE)
			.addPathPatterns("/articles/**", "/users/**", "/reply/**", "/post/**")
			.excludePathPatterns("/user/join", "/login", "/css/**", "images/**", "/js/**",
				"/*.ico");
	}
}
