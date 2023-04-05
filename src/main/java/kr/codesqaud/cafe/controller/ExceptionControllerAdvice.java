package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ErrorDto;
import kr.codesqaud.cafe.exception.user.UserJoinFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public String notFoundExceptionHandler(Exception ex, Model model) {
        model.addAttribute("error", new ErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage()));

        return "error/error_page";
    }

    @ExceptionHandler(UserJoinFailedException.class)
    public String userJoinDuplicatedExceptionHandler(UserJoinFailedException ex, Model model) {
        model.addAttribute("user", ex.getUserJoinDto());
        model.addAttribute(ex.getErrorField(), ex.getMessage());

        return "user/form";
    }
}
