package kr.codesqaud.cafe.global;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.codesqaud.cafe.account.exception.AlreadyUserExistenceException;
import kr.codesqaud.cafe.account.exception.LoginInvalidPasswordException;
import kr.codesqaud.cafe.account.exception.UserNotFoundException;
import kr.codesqaud.cafe.account.exception.UserUpdateInvalidPasswordException;
import kr.codesqaud.cafe.article.exception.ArticleDeleteFailedException;
import kr.codesqaud.cafe.article.exception.ArticleIdAndSessionIdMismatchException;
import kr.codesqaud.cafe.article.exception.ArticleNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private ModelAndView createErrorResponseModelAndView(String viewName, Exception e) {
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("error", e.getMessage());
		return mav;
	}

	private String handleExceptionWithRedirect(Exception e, String redirectUri, String attributeName,
		RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(attributeName, e.getMessage());
		return "redirect:" + redirectUri;
	}

	/**
	 * @Valid에 의해 발생할수있는 3가지 BindException 경우에대한 예외처리 메서드입니다.
	 * @param request
	 * @param redirectAttributes
	 * @return String
	 */
	@ExceptionHandler(BindException.class)
	public String handleBindException(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String requestUri = request.getRequestURI();
		if (requestUri.contains("/sign-up")) {
			return "redirect:/users/sign-up";
		} else if (requestUri.contains("/users")) {
			redirectAttributes.addAttribute("id", request.getParameter("id"));
			return "redirect:/users/updateForm";
		}
		return "redirect:/articles";
	}

	@ExceptionHandler(AlreadyUserExistenceException.class)
	public ModelAndView handleAlreadyUserExistenceException(AlreadyUserExistenceException e,
		RedirectAttributes redirectAttributes) {
		return new ModelAndView(handleExceptionWithRedirect(e, "/users/sign-up", "id-error", redirectAttributes));
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ModelAndView handleUserNotFoundException(UserNotFoundException e, HttpServletRequest request,
		RedirectAttributes redirectAttributes) {
		String requestUri = request.getRequestURI();
		if (requestUri.contains("/users")) {
			return createErrorResponseModelAndView("error/400_bad_request", e);
		}
		return new ModelAndView(handleExceptionWithRedirect(e, "/users/sign-in", "id-error", redirectAttributes));
	}

	@ExceptionHandler(UserUpdateInvalidPasswordException.class)
	public ModelAndView handleUserUpdateInvalidPasswordException(UserUpdateInvalidPasswordException e,
		RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(
			handleExceptionWithRedirect(e, "/users/updateForm", "password-error",
				redirectAttributes));
		return mav;
	}

	@ExceptionHandler(LoginInvalidPasswordException.class)
	public ModelAndView handleLoginInvalidPasswordException(LoginInvalidPasswordException e,
		RedirectAttributes redirectAttributes) {
		return new ModelAndView(
			handleExceptionWithRedirect(e, "/users/sign-in", "password-error", redirectAttributes));
	}

	@ExceptionHandler(ArticleNotFoundException.class)
	public ModelAndView handleArticleNotFoundException(ArticleNotFoundException e) {
		return createErrorResponseModelAndView("error/400_bad_request", e);
	}

	@ExceptionHandler(ArticleIdAndSessionIdMismatchException.class)
	public ModelAndView handleArticleIdAndSessionIdMismatchException(ArticleIdAndSessionIdMismatchException e) {
		return createErrorResponseModelAndView("error/401_unauthorized", e);
	}

	@ExceptionHandler(ArticleDeleteFailedException.class)
	public ModelAndView handleArticleDeleteFailedException(ArticleDeleteFailedException e) {
		return createErrorResponseModelAndView("error/401_unauthorized", e);
	}
}
