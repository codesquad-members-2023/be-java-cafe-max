package kr.codesqaud.cafe.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.codesqaud.cafe.common.argumentresolver.PageableArgumentResolver;
import kr.codesqaud.cafe.common.interceptor.LoginInterceptor;

@Configuration(proxyBeanMethods = false)
public class MvcConfig implements WebMvcConfigurer {

	private final LoginInterceptor loginInterceptor;
	private final PageableArgumentResolver pageableArgumentResolver;

	public MvcConfig(LoginInterceptor loginInterceptor, PageableArgumentResolver pageableArgumentResolver) {
		this.loginInterceptor = loginInterceptor;
		this.pageableArgumentResolver = pageableArgumentResolver;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registry.addViewController("/user/form").setViewName("user/form");
		registry.addViewController("/user/login").setViewName("user/login");
		registry.addViewController("/question/form").setViewName("qna/form");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String[] authorizePatterns = new String[] {"/articles/**", "/question/**", "/comments/**"};

		registry
			.addInterceptor(loginInterceptor)
			.addPathPatterns(authorizePatterns);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(pageableArgumentResolver);
	}
}
