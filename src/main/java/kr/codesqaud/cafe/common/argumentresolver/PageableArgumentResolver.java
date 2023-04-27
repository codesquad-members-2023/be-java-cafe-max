package kr.codesqaud.cafe.common.argumentresolver;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.codesqaud.cafe.service.paging.Pageable;

@Component
public class PageableArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().getSimpleName().equals("Pageable");
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
	                              NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		return Optional.ofNullable(request.getQueryString())
			.filter(queryString -> queryString != null && !queryString.isEmpty())
			.map(queryString -> queryString.split("=")[1])
			.map(Integer::parseInt)
			.map(Pageable::new)
			.orElseGet(() -> new Pageable(0));
	}
}
