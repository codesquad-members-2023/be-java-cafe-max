package kr.codesqaud.cafe.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicatedUserIdException.class)
	public String handleDuplicatedUserId(final DuplicatedUserIdException e, final Model model) {
		model.addAttribute("error", e.getMessage());
		return "user/form";
	}

	@ExceptionHandler(NotFoundException.class)
	public String handleUserNotFound(final NotFoundException e, final Model model) {
		model.addAttribute("error", e.getMessage());
		return "user/notfound";
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public String handleInvalidPassword(final InvalidPasswordException e, final Model model) {
		model.addAttribute("error", e.getMessage());
		return "user/edit_form";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(final Exception e) {
		return "에러!";
	}
}
