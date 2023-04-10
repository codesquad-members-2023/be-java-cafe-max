package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.exception.DeniedAccessException;
import kr.codesqaud.cafe.exception.LoginFailedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoginFailedException.class)
    public String loginFailedPage(LoginFailedException e, Model model){
        model.addAttribute("error",e.getMessage());
        return "user/error";
    }

    @ExceptionHandler(DeniedAccessException.class)
    public String deniedAccessPage(DeniedAccessException e,Model model){
        model.addAttribute("error",e.getMessage());
        return "user/error";
    }
}
