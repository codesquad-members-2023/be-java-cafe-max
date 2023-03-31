package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView notFoundExceptionHandler(Exception ex) {
        final ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("error/error_page");
        modelAndView.addObject("error", new ErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
        return modelAndView;
    }
}
