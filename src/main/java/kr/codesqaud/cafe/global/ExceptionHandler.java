package kr.codesqaud.cafe.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public String executeRuntimeErrorHandler(HttpServletRequest request) {
        logger.warn("request:url {}", request.getRequestURI());
        return "error/4xx";
    }
}
