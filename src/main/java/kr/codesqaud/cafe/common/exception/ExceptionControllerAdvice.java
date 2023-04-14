package kr.codesqaud.cafe.common.exception;

import kr.codesqaud.cafe.common.exception.user.UserJoinException;
import kr.codesqaud.cafe.common.exception.user.UserLoginException;
import kr.codesqaud.cafe.controller.dto.ErrorDto;
import kr.codesqaud.cafe.common.exception.user.UserExceptionType;
import kr.codesqaud.cafe.common.exception.user.UserUpdateException;
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

    @ExceptionHandler(UserJoinException.class)
    public String userJoinExceptionHandler(UserJoinException ex, Model model) {
        final UserExceptionType type = ex.getUserExceptionType();

        model.addAttribute("user", ex.getUserJoinDto());
        model.addAttribute(type.getCategory(), type.getMessage());

        return "user/form";
    }

    @ExceptionHandler(UserUpdateException.class)
    public String userJoinDuplicatedExceptionHandler(UserUpdateException ex, Model model) {
        final UserExceptionType type = ex.getUserExceptionType();

        model.addAttribute("user", ex.getUserUpdateDto());
        model.addAttribute(type.getCategory(), type.getMessage());

        return "user/update";
    }

    @ExceptionHandler(UserLoginException.class)
    public String userLoginFailedExceptionHandler(UserLoginException ex, Model model) {
        final UserExceptionType type = ex.getUserExceptionType();

        model.addAttribute("loginUser", ex.getUserLoginDto());
        model.addAttribute(type.getCategory(), type.getMessage());

        return "user/login";
    }
}
