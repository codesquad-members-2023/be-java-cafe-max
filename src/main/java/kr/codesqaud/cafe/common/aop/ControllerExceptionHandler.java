package kr.codesqaud.cafe.common.aop;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.codesqaud.cafe.question.exception.QuestionNotExistException;
import kr.codesqaud.cafe.user.exception.UserDoesNotMatchException;
import kr.codesqaud.cafe.user.exception.UserIdDuplicateException;
import kr.codesqaud.cafe.user.exception.UserNotExistException;

@ControllerAdvice
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

	@ExceptionHandler(QuestionNotExistException.class)
	public String catchNoSuchElementException(HttpServletRequest request, RedirectAttributes redirect,
		QuestionNotExistException e) {
		redirect.addFlashAttribute(ERROR_MESSAGE_NAME, e.getMessage());
		return getRedirectRequestURI(request);
	}

	@ExceptionHandler({UserIdDuplicateException.class, UserNotExistException.class})
	public String catchIllegalArgumentException(HttpServletRequest request, RedirectAttributes redirect,
		Exception e) {
		redirect.addFlashAttribute(ERROR_MESSAGE_NAME, e.getMessage());

		if (request.getRequestURI().equals("/users")) {
			return "redirect:/users/signup";
		}

		return "error/400-bad-request";
	}

	@ExceptionHandler(UserDoesNotMatchException.class)
	public String catchUserDoesNotMatchException(RedirectAttributes redirect,
		Exception e) {
		redirect.addFlashAttribute(ERROR_MESSAGE_NAME, e.getMessage());

		return "redirect:/users/signin";
	}

}
