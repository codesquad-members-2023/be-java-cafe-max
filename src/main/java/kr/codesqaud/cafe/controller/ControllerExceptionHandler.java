package kr.codesqaud.cafe.controller;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice("kr.codesqaud.cafe.controller")
public class ControllerExceptionHandler {
	private static final String ERROR_MESSAGE_NAME = "errorMessage";

	private String getRedirectRequestURI(HttpServletRequest request) {
		return "redirect:" + request.getRequestURI();
	}

	@ExceptionHandler(NumberFormatException.class)
	public String catchNumberFormatException(HttpServletRequest request, RedirectAttributes redirect) {
		redirect.addFlashAttribute(ERROR_MESSAGE_NAME, "잘못된 값이 입력되었습니다.");
		return getRedirectRequestURI(request);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public String catchNoSuchElementException(HttpServletRequest request, RedirectAttributes redirect,
		NoSuchElementException e) {
		redirect.addFlashAttribute(ERROR_MESSAGE_NAME, e.getMessage());
		return getRedirectRequestURI(request);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public String catchIllegalArgumentException(HttpServletRequest request, RedirectAttributes redirect,
		IllegalArgumentException e) {
		redirect.addFlashAttribute(ERROR_MESSAGE_NAME, e.getMessage());

		if (request.getRequestURI().equals("/users")) {
			return "redirect:/users/signup";
		}

		return "error/400-bad-request";
	}

}
