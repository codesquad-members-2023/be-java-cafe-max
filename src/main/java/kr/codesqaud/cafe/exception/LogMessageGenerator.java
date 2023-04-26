package kr.codesqaud.cafe.exception;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.util.ContentCachingRequestWrapper;

public final class LogMessageGenerator {

	private static final String MESSAGE_FORMAT = "[REQUEST]\n[%s] %s\n%s\n[PARAMETERS] %s\n[BODY] %s\n[EXCEPTION MESSAGE] %s";

	private LogMessageGenerator() {
	}

	public static String generate(final ContentCachingRequestWrapper request, final Exception exception) {
		String method = request.getMethod();
		String requestUri = request.getRequestURI();
		String headers = getHeaders(request);
		String params = getParams(request);
		String body = new String(request.getContentAsByteArray());

		return String.format(MESSAGE_FORMAT, method, requestUri, headers, params, body, exception.getMessage());
	}

	private static String getHeaders(final ContentCachingRequestWrapper request) {
		Enumeration<String> headerNames = request.getHeaderNames();

		Map<String, String> headers = new HashMap<>();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			headers.put(headerName, request.getHeader(headerName));
		}
		return convertMapToString(headers);
	}

	private static String getParams(final ContentCachingRequestWrapper request) {
		Enumeration<String> parameterNames = request.getParameterNames();

		Map<String, String> params = new HashMap<>();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			params.put(paramName, request.getParameter(paramName));
		}
		return convertMapToString(params);
	}

	private static String convertMapToString(Map<String, String> headers) {
		return headers.entrySet().stream()
			.map(entry -> entry.getKey().concat(" : ").concat(entry.getValue()))
			.collect(Collectors.joining("\n"));
	}
}
