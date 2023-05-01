package kr.codesqaud.cafe.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;

import kr.codesqaud.cafe.dto.error.ErrorDto;
import kr.codesqaud.cafe.exception.common.CommonException;
import kr.codesqaud.cafe.exception.common.CommonExceptionType;
import kr.codesqaud.cafe.exception.member.MemberExceptionType;
import kr.codesqaud.cafe.exception.member.MemberJoinException;
import kr.codesqaud.cafe.exception.member.MemberLoginException;
import kr.codesqaud.cafe.exception.member.MemberProfileEditException;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = NoSuchElementException.class)
    public String handleNoSuchElementException() {
        return "error/404";
    }

    @ExceptionHandler(CommonException.class)
    public ModelAndView commonExceptionHandler(CommonException commonException) {
        ModelAndView modelAndView = new ModelAndView();
        CommonExceptionType commonExceptionType = commonException.getCommonExceptionType();

        modelAndView.setViewName("error/error_page");
        modelAndView.addObject("error",new ErrorDto(commonExceptionType.getStatus().value(),commonExceptionType.getErrorMessage()));
        modelAndView.setStatus(commonExceptionType.getStatus());
        return modelAndView;
    }

    @ExceptionHandler(MemberJoinException.class)
    public String userJoinExceptionHandler(MemberJoinException memberJoinException, Model model) {
        MemberExceptionType memberExceptionType = memberJoinException.getMemberExceptionType();
        model.addAttribute("member");
        model.addAttribute(memberExceptionType.getCategory(), memberExceptionType.getMessage());
        return "member/register";
    }

    @ExceptionHandler(MemberProfileEditException.class)
    public String userDuplicatedInfoExceptionHandler(MemberProfileEditException memberProfileEditException, Model model) {
        MemberExceptionType memberExceptionType = memberProfileEditException.getMemberExceptionType();

        model.addAttribute("profileEditRequestDto");
        model.addAttribute(memberExceptionType.getCategory(), memberExceptionType.getMessage());

        return "user/update";
    }

    @ExceptionHandler(MemberLoginException.class)
    public String userLoginFailedExceptionHandler(MemberLoginException memberLoginException, Model model) {
        MemberExceptionType memberExceptionType = memberLoginException.getMemberExceptionType();

        model.addAttribute("loginMember");
        model.addAttribute(memberExceptionType.getCategory(), memberExceptionType.getMessage());

        return "member/login";
    }


}
