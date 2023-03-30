package kr.codesqaud.cafe.exception.controller;

import kr.codesqaud.cafe.exception.common.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public String badRequest(BadRequestException e, Model model) {
        logger.error("error code : {}, error message : {}, error data = {}", e.getErrorCode(),
            e.getMessage(), e.getErrorData());
        model.addAttribute("errorCode", e.getErrorCode());
        return "error/400";
    }
}
