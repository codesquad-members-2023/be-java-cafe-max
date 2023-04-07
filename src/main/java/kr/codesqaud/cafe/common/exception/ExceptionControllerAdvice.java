package kr.codesqaud.cafe.common.exception;

import kr.codesqaud.cafe.controller.dto.ErrorDto;
import kr.codesqaud.cafe.common.exception.user.UserExceptionType;
import kr.codesqaud.cafe.common.exception.user.UserFormInputException;
import kr.codesqaud.cafe.controller.dto.user.UserFormDto;
import kr.codesqaud.cafe.controller.dto.user.UserUpdateDto;
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

    @ExceptionHandler(UserFormInputException.class)
    public String userJoinDuplicatedExceptionHandler(UserFormInputException ex, Model model) {
        final UserExceptionType exceptionType = ex.getUserExceptionType();
        final UserFormDto userFormDto = ex.getUserFormDto();

        model.addAttribute("user", userFormDto);
        model.addAttribute(exceptionType.getCategory(), exceptionType.getMessage());

        if (userFormDto instanceof UserUpdateDto) {
            return "user/update";
        }

        return "user/form";
    }
}
