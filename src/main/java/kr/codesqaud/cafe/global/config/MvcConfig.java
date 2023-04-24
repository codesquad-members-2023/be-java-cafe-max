package kr.codesqaud.cafe.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SignInInterceptor())
			.addPathPatterns("/users/**", "/articles/**")
			.excludePathPatterns("/", "/users/sign-in", "/users/sign-up", "/users/list");
	}
}
