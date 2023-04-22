package kr.codesqaud.cafe.common.exception;

import kr.codesqaud.cafe.common.exception.user.UserExceptionType;
import kr.codesqaud.cafe.common.exception.user.UserJoinException;
import kr.codesqaud.cafe.common.exception.user.UserLoginException;
import kr.codesqaud.cafe.common.exception.user.UserUpdateException;
import kr.codesqaud.cafe.controller.dto.ErrorDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(CommonException.class)
    public ModelAndView commonExceptionHandler(CommonException ex) {
        final ModelAndView mav = new ModelAndView();
        final CommonExceptionType exceptionType = ex.getExceptionType();

        mav.setViewName("error/error_page");
        mav.addObject("error", new ErrorDto(exceptionType.getStatusValue(), exceptionType.getErrorMessage()));
        mav.setStatus(exceptionType.getStatus());

        return mav;
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
