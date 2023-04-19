package kr.codesqaud.cafe.global.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.codesqaud.cafe.global.exception.user.DuplicatedUserIdException;
import kr.codesqaud.cafe.global.exception.user.InvalidLoginInfoException;
import kr.codesqaud.cafe.global.exception.user.InvalidPasswordException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicatedUserIdException.class)
	public String handleDuplicatedUserId(final DuplicatedUserIdException e, final Model model) {
		model.addAttribute("error", e.getMessage());
		return "user/form";
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public String handleInvalidPassword(final InvalidPasswordException e, final Model model) {
		model.addAttribute("error", e.getMessage());
		return "user/updateForm";
	}

	@ExceptionHandler(InvalidLoginInfoException.class)
	public String handleInvalidLoginInfo(final InvalidLoginInfoException e, final Model model) {
		model.addAttribute("error", e.getMessage());
		return "user/login";
	}
}
