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
}
