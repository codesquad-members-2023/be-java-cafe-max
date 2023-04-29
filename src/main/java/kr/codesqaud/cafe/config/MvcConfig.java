package kr.codesqaud.cafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.codesqaud.cafe.interceptor.LoginInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	private final LoginInterceptor loginInterceptor;

	public MvcConfig(LoginInterceptor loginInterceptor) {
		this.loginInterceptor = loginInterceptor;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registry.addViewController("/user/form").setViewName("user/form");
		registry.addViewController("/users/login").setViewName("user/login");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns(new String[] {"/css/**", "/js/**", "/fonts/**", "/images/**"})
			.excludePathPatterns(new String[] {"/user/login", "/users/login", "/user/form", "/user", "/"});
	}
}
