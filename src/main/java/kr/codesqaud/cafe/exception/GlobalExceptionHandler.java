package kr.codesqaud.cafe.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public String handleUserNotFound(final NotFoundException e, final Model model) {
		model.addAttribute("error", e.getMessage());
		return "user/notfound";
	}

	@ExceptionHandler(InvalidLoginInfoException.class)
	public String handleInvalidLoginInfo(final InvalidLoginInfoException e, final Model model) {
		model.addAttribute("error", e.getMessage());
		return "user/login";
	}

	@ExceptionHandler({InvalidSessionException.class, NoAuthorizationException.class, InvalidOperationException.class})
	public String handleInvalidSession(final RuntimeException e, final Model model) {
		model.addAttribute("error", e.getMessage());
		return "error";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(final Exception e, final Model model) {
		model.addAttribute("error", "죄송합니다. 서버 에러가 발생했습니다.");
		return "error";
	}
}
