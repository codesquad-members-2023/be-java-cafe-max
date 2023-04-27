package kr.codesqaud.cafe.common.aop;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.codesqaud.cafe.common.auth.exception.NoAccessPermissionException;
import kr.codesqaud.cafe.common.auth.exception.NoAuthSessionException;
import kr.codesqaud.cafe.question.exception.QuestionNotExistException;
import kr.codesqaud.cafe.question_comment.exception.CommentNotExistException;
import kr.codesqaud.cafe.user.exception.UserDoesNotMatchException;
import kr.codesqaud.cafe.user.exception.UserIdDuplicateException;
import kr.codesqaud.cafe.user.exception.UserNotExistException;

@ControllerAdvice
public class ControllerExceptionHandler {
	private static final String ERROR_MESSAGE_NAME = "errorMessage";
	private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(NumberFormatException.class)
	public String catchNumberFormatException(HttpServletRequest request, RedirectAttributes redirect,
		NumberFormatException e) {
		String errorMessage = "잘못된 값이 입력되었습니다.";
		logger.warn("{} {}", errorMessage, e.getStackTrace()[3].toString());
		redirect.addFlashAttribute(ERROR_MESSAGE_NAME, errorMessage);
		return getRedirectRequestURI(request);
	}

	@ExceptionHandler({QuestionNotExistException.class, CommentNotExistException.class})
	public String catchNoSuchElementException(HttpServletRequest request, RedirectAttributes redirect,
		QuestionNotExistException e) {
		printExceptionLogger(e);
		redirect.addFlashAttribute(ERROR_MESSAGE_NAME, e.getMessage());
		return getRedirectRequestURI(request);
	}

	@ExceptionHandler({UserIdDuplicateException.class, UserNotExistException.class, UserDoesNotMatchException.class})
	public String catchIllegalArgumentException(HttpServletRequest request, RedirectAttributes redirect,
		Exception e) {
		printExceptionLogger(e);
		redirect.addFlashAttribute(ERROR_MESSAGE_NAME, e.getMessage());

		if (request.getRequestURI().equals("/users")) {
			return "redirect:/users/signup";
		}

		return getRedirectRequestURI(request);
	}

	@ExceptionHandler({NoAuthSessionException.class, NoAccessPermissionException.class})
	public String catchNoAuthSessionException(HttpServletRequest request, Model model, Exception e) {
		model.addAttribute("authMessage", e.getMessage());
		return "error/403-forbidden";
	}

	private String getRedirectRequestURI(HttpServletRequest request) {
		return "redirect:" + request.getRequestURI();
	}

	private void printExceptionLogger(Exception e) {
		logger.warn("{} {}", e.getMessage(), e.getStackTrace()[0].toString());
	}

}
