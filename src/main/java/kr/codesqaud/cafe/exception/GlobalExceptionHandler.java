package kr.codesqaud.cafe.exception;

import static kr.codesqaud.cafe.exception.LogMessageGenerator.generate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.codesqaud.cafe.common.filter.RequestContext;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	private final RequestContext requestContext;

	public GlobalExceptionHandler(final RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	@ExceptionHandler(NotFoundException.class)
	public String handleUserNotFound(final NotFoundException e, final Model model) {
		log.info(generate(requestContext.getRequest(), e));
		model.addAttribute("error", e.getMessage());
		return "user/notfound";
	}

	@ExceptionHandler(InvalidLoginInfoException.class)
	public String handleInvalidLoginInfo(final InvalidLoginInfoException e, final Model model) {
		log.info(generate(requestContext.getRequest(), e));
		model.addAttribute("error", e.getMessage());
		return "user/login";
	}

	@ExceptionHandler(BusinessException.class)
	public String handleBusiness(final RuntimeException e, final Model model) {
		log.info(generate(requestContext.getRequest(), e));
		model.addAttribute("error", e.getMessage());
		return "error";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(final Exception e, final Model model) {
		log.warn(generate(requestContext.getRequest(), e));
		model.addAttribute("error", "죄송합니다. 서버 에러가 발생했습니다.");
		return "error";
	}
}
