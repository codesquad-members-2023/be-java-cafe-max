package kr.codesqaud.cafe.exception;

import kr.codesqaud.cafe.exception.article.ArticleNotFoundException;
import kr.codesqaud.cafe.exception.user.AccessDeniedException;
import kr.codesqaud.cafe.exception.user.AlreadyUserExistenceException;
import kr.codesqaud.cafe.exception.user.LoginFailedException;
import kr.codesqaud.cafe.exception.user.MismatchedPasswordException;
import kr.codesqaud.cafe.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(AlreadyUserExistenceException.class)
    public String handleDuplicateUserIdException(AlreadyUserExistenceException exception, Model model) {
        model.addAttribute("userSaveRequest", exception.getUserSaveRequest());
        model.addAttribute("duplicateUserIdMessage", exception.getMessage());
        return "user/sign-up";
    }

    @ExceptionHandler(MismatchedPasswordException.class)
    public String handleMismatchedPasswordException(MismatchedPasswordException exception, Model model) {
        model.addAttribute("user", exception.getUserUpdateRequest());
        model.addAttribute("mismatchedPasswordMessage", exception.getMessage());
        return "user/update";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException exception, Model model) {
        model.addAttribute("failMessage", exception.getMessage());
        return "exception/fail";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ArticleNotFoundException.class)
    public String handelArticleNotFoundException(ArticleNotFoundException exception, Model model) {
        model.addAttribute("failMessage", exception.getMessage());
        return "exception/fail";
    }

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailedException(LoginFailedException exception, Model model) {
        model.addAttribute("loginFailedMessage", exception.getMessage());
        return "user/login";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException exception, Model model) {
        model.addAttribute("accessDeniedMessage", exception.getMessage());
        return "exception/forbidden";
    }
}
