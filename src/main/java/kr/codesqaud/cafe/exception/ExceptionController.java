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
	public String invalidException(BindException e, HttpServletRequest request, HttpSession session,
		RedirectAttributes redirectAttributes) {
		String requestUri = request.getRequestURI();
		ErrorResponse errorResponse = makeErrorResponse(e.getBindingResult());
		redirectAttributes.addFlashAttribute("errorMessage", errorResponse.getDetail());
		if (requestUri.contains("/create")) {
			return "redirect:/user/form";
		} else if (requestUri.contains("/update")) {
			User user = (User)session.getAttribute("sessionUser");
			redirectAttributes.addFlashAttribute("user", user);
			return "redirect:/user/updateForm";
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
		RedirectAttributes redirectAttributes) {
		String requestUri = request.getRequestURI();
		redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		if (requestUri.contains("/create")) {
			return "redirect:/user/form";
		} else if (requestUri.contains("/update")) {
			User user = (User)session.getAttribute("sessionUser");
			redirectAttributes.addFlashAttribute("user", user);
			return "redirect:/user/updateForm";
		}
		return "/";
	}

	@ExceptionHandler(UserNotFoundException.class)
	public String userNotFoundException(UserNotFoundException e, HttpServletRequest request,
		RedirectAttributes redirectAttributes) {
		String requestUri = request.getRequestURI();
		redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		if (requestUri.contains("/create")) {
			return "redirect:/user/form";
		}
		return "redirect:user/login";
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public String invalidPasswordException(InvalidPasswordException e, HttpServletRequest request,
		RedirectAttributes redirectAttributes) {
		String requestUri = request.getRequestURI();
		redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		if (requestUri.contains("/form")) {
			String userId = requestUri.split("/")[3];
			return "redirect:/check/" + userId;
		}
		return "redirect:user/login";
	}
}
