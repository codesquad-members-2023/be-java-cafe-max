package kr.codesqaud.cafe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView notFoundExceptionHandler(Exception ex) {
        final ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("error/error_page");
        modelAndView.addAllObjects(Map.of(
                "errorStatus", HttpStatus.NOT_FOUND.value(),
                "errorMessage", "해당 리소스를 찾을 수 없습니다."
        ));
        return modelAndView;
    }
}
