package kr.codesqaud.cafe.exception.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.exception.common.BadRequestException;
import kr.codesqaud.cafe.exception.member.DuplicateMemberEmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @ExceptionHandler(DuplicateMemberEmailException.class)
    public String email(DuplicateMemberEmailException e, Model model)
        throws JsonProcessingException {
        String objectName = "signUpRequest";
        String viewName = "member/signUp";

        if (ProfileEditRequest.class.isAssignableFrom(e.getErrorData().getClass())) {
            objectName = "profileEditRequest";
            viewName = "member/profileEdit";
        }

        BindingResult bindingResult = new BeanPropertyBindingResult(e.getErrorData(), objectName);
        bindingResult.rejectValue("email", "duplicate");
        model.addAttribute(String.format("org.springframework.validation.BindingResult.%s",
            bindingResult.getObjectName()), bindingResult);
        model.addAttribute(objectName, e.getErrorData());
        return viewName;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public String badRequest(BadRequestException e, Model model) {
        logger.error("error code : {}, error message : {}, error data = {}", e.getErrorCode(),
            e.getMessage(), e.getErrorData());
        model.addAttribute("errorCode", e.getErrorCode());
        model.addAttribute("errorMessage", e.getMessage());
        return "error/400";
    }
}
