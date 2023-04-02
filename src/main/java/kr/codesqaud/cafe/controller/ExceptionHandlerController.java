package kr.codesqaud.cafe.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = NoSuchElementException.class)
    public String handleNoSuchElementException() {
        return "error/404";
    }

}
