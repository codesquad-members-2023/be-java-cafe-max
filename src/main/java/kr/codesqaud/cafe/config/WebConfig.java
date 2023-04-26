package kr.codesqaud.cafe.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.codesqaud.cafe.interceptor.LoginInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/", "/login", "/users", "/logout", "/users/form", "/css/**",
				"/js/**", "/error/**", "/images/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**")
			.addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**")
			.addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/images/**")
			.addResourceLocations("classpath:/static/images/");
	}
}
