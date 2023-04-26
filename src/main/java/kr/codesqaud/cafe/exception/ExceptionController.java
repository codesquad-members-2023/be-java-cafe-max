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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.codesqaud.cafe.domain.User;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(BindException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String invalidException(BindException e, HttpServletRequest request, HttpSession session,
		RedirectAttributes redirectAttributes) {
		String requestUri = request.getRequestURI();
		ErrorResponse errorResponse = makeErrorResponse(e.getBindingResult());
		redirectAttributes.addFlashAttribute("errorMessage", errorResponse.getDetail());
		if (requestUri.contains("/users/")) {
			User user = (User)session.getAttribute("sessionUser");
			redirectAttributes.addFlashAttribute("user", user);
			return "redirect:/users/update-form";
		}
		return "redirect:/users/form";
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

	@ExceptionHandler(DeniedArticleModificationException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public String deniedArticleModificationException(DeniedArticleModificationException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/error";
	}

	@ExceptionHandler(DeniedUserModificationException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public String deniedUserModificationException(DeniedUserModificationException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/error";
	}

	@ExceptionHandler(DeniedCommentModificationException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public String deniedCommentModificationException(DeniedCommentModificationException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/error";
	}

	@ExceptionHandler(OtherCommentExistsException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public String otherCommentExistsException(OtherCommentExistsException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/error";
	}

	@ExceptionHandler(InvalidAccessException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public String invalidAccessException(InvalidAccessException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/error";
	}

	@ExceptionHandler(DuplicateUserException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String duplicateUserException(DuplicateUserException e, HttpServletRequest request, HttpSession session,
		RedirectAttributes redirectAttributes) {
		String requestUri = request.getRequestURI();
		redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		if (requestUri.contains("/users/")) {
			User user = (User)session.getAttribute("sessionUser");
			redirectAttributes.addFlashAttribute("user", user);
			return "redirect:/users/update-form";
		}
		return "redirect:/users/form";
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String userNotFoundException(UserNotFoundException e, HttpServletRequest request,
		RedirectAttributes redirectAttributes) {
		String requestUri = request.getRequestURI();
		redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		if (requestUri.contains("/login")) {
			return "redirect:/login";
		}
		return "redirect:/users/form";
	}

	@ExceptionHandler(InvalidPasswordException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String invalidPasswordException(InvalidPasswordException e, HttpServletRequest request,
		RedirectAttributes redirectAttributes) {
		String requestUri = request.getRequestURI();
		redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		if (requestUri.contains("/users")) {
			String userId = requestUri.split("/")[2];
			return "redirect:/users/check/" + userId;
		}
		return "redirect:/login";
	}
}
