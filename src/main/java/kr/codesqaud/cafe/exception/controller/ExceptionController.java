package kr.codesqaud.cafe.exception.controller;

import kr.codesqaud.cafe.exception.common.BadRequestException;
import kr.codesqaud.cafe.exception.common.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public String badRequest(BadRequestException e, Model model) {
        BindingResult bindingResult = new BeanPropertyBindingResult(e.getErrorData(), getClassName(e));
        bindingResult.rejectValue(e.getField(), e.getErrorCode());
        model.addAttribute(String.format("org.springframework.validation.BindingResult.%s",
            bindingResult.getObjectName()), bindingResult);
        model.addAttribute(bindingResult.getObjectName(), e.getErrorData());
        return e.getViewName();
    }

    private String getClassName(BadRequestException e) {
        String className = e.getErrorData().getClass().getSimpleName();
        return className.replace(className.charAt(0), (char) (className.charAt(0) + 32));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String notFound(NotFoundException e, Model model) {
        logger.error("NotFoundException\n error message : {}", e.getErrorMessage());
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
