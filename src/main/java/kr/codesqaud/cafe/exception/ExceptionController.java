package kr.codesqaud.cafe.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import kr.codesqaud.cafe.domain.User;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(BindException.class)
	public String invalidException(BindException e, HttpServletRequest request, HttpSession session, Model model) {
		String requestUri = request.getRequestURI();
		ErrorResponse errorResponse = makeErrorResponse(e.getBindingResult());
		model.addAttribute("errorMessage", errorResponse.getDetail());
		if (requestUri.contains("/create")) {
			return "/user/form";
		} else if (requestUri.contains("/update")) {
			User user = (User)session.getAttribute("sessionUser");
			model.addAttribute("user", user);
			return "/user/updateForm";
		}
		return "/";
	}

	private ErrorResponse makeErrorResponse(BindingResult bindingResult) {
		StringBuilder detail = new StringBuilder();

		if (bindingResult.hasErrors()) {
			List<FieldError> list = bindingResult.getFieldErrors();
			for (FieldError error : list) {
				detail.append(error.getDefaultMessage()).append("<br>");
			}
		}
		return new ErrorResponse(detail.toString());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String noHandlerException(Model model) {
		model.addAttribute("errorMessage", "페이지를 찾을 수 없습니다.");
		return "error/error";
	}

	@ExceptionHandler(DeniedDataModificationException.class)
	public String deniedDataException(DeniedDataModificationException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/error";
	}

	@ExceptionHandler(DuplicateUserException.class)
	public String duplicateUserException(DuplicateUserException e, HttpServletRequest request, HttpSession session,
		Model model) {
		String requestUri = request.getRequestURI();
		model.addAttribute("errorMessage", e.getMessage());
		if (requestUri.contains("/create")) {
			return "/user/form";
		} else if (requestUri.contains("/update")) {
			User user = (User)session.getAttribute("sessionUser");
			model.addAttribute("user", user);
			return "/user/updateForm";
		}
		return "/";
	}
}
