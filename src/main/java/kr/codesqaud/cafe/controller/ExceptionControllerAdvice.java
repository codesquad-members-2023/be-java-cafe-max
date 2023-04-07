package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ErrorDto;
import kr.codesqaud.cafe.common.exception.user.UserExceptionType;
import kr.codesqaud.cafe.common.exception.user.UserJoinFailedException;
import kr.codesqaud.cafe.common.exception.user.UserUpdateFailedException;
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
        final UserExceptionType exceptionType = ex.getUserExceptionType();
        model.addAttribute("user", ex.getUserJoinDto());
        model.addAttribute(exceptionType.getCategory(), exceptionType.getMessage());

        return "user/form";
    }

    @ExceptionHandler(UserUpdateFailedException.class)
    public String userUpdateException(UserUpdateFailedException ex, Model model) {
        final UserExceptionType userExceptionType = ex.getUserExceptionType();
        model.addAttribute("user", ex.getUserUpdateDto());
        model.addAttribute(userExceptionType.getCategory(), userExceptionType.getMessage());

        return "user/update";
    }
}
