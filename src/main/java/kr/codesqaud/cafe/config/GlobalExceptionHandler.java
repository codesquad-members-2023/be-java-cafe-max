package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.exception.common.NotFoundException;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorized(UnauthorizedException e, Model model) {
        logger.error("Unauthorized", e);
        model.addAttribute("status", HttpStatus.UNAUTHORIZED.value());
        model.addAttribute("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        model.addAttribute("message", e.getMessage());
        return "error/4xx";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String notFound(NotFoundException e, Model model) {
        logger.error("NotFoundException", e);
        model.addAttribute("errorMessage", e.getMessage());
        return "error/404";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public String runtime(RuntimeException e) {
        logger.error("RuntimeException", e);
        return "error/500";
    }
}
