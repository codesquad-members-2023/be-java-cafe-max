package kr.codesquad.cafe.user.controller;

import kr.codesquad.cafe.user.dto.JoinForm;
import kr.codesquad.cafe.user.dto.LoginForm;
import kr.codesquad.cafe.user.dto.ProfileEditForm;
import kr.codesquad.cafe.user.exception.DuplicateEmailException;
import kr.codesquad.cafe.user.exception.ExistsEmailException;
import kr.codesquad.cafe.user.exception.IncorrectPasswordException;
import kr.codesquad.cafe.user.exception.InvalidPasswordException;
import kr.codesquad.cafe.user.exception.UserNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice("kr.codesquad.cafe.user")
public class UserControllerExceptionHandler {

    private static final String EMAIL_ERROR = "emailError";
    private static final String PASSWORD_ERROR = "passwordError";
    private static final String EMAIL = "email";
    private static final String NICK_NAME = "nickname";
    private static final String EMPTY_PASSWORD = "";

    private static void addJoinForm(HttpServletRequest request, Model model) {
        String email = request.getParameter(EMAIL);
        String nickName = request.getParameter(NICK_NAME);
        model.addAttribute(new JoinForm(nickName, email, EMPTY_PASSWORD, EMPTY_PASSWORD));
    }

    private static void addProfileEditForm(HttpServletRequest request, Model model) {
        String email = request.getParameter(EMAIL);
        String nickName = request.getParameter(NICK_NAME);
        model.addAttribute(new ProfileEditForm(nickName, email, EMPTY_PASSWORD));
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(IncorrectPasswordException.class)
    public String handlerIllegalLoginPasswordException(Model model, IncorrectPasswordException e) {
        model.addAttribute(new LoginForm());
        model.addAttribute(PASSWORD_ERROR, e.getMessage());
        return "user/loginFailed";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String handlerNoSuchLoginEmailException(Model model, UserNotFoundException e) {
        model.addAttribute(new LoginForm());
        model.addAttribute(EMAIL_ERROR, e.getMessage());
        return "user/loginFailed";
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(DuplicateEmailException.class)
    public String handlerIllegalEditEmailException(HttpServletRequest request, Model model, DuplicateEmailException e) {
        addProfileEditForm(request, model);
        model.addAttribute(EMAIL_ERROR, e.getMessage());
        return "user/profileEditFormFailed";
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidPasswordException.class)
    public String handlerIllegalEditPasswordException(HttpServletRequest request, Model model, InvalidPasswordException e) {
        addProfileEditForm(request, model);
        model.addAttribute(PASSWORD_ERROR, e.getMessage());
        return "user/profileEditFormFailed";
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(ExistsEmailException.class)
    public String handlerExistsEmailException(HttpServletRequest request, Model model, ExistsEmailException e) {
        addJoinForm(request, model);
        model.addAttribute(EMAIL_ERROR, e.getMessage());
        return "user/joinFailed";
    }
}
