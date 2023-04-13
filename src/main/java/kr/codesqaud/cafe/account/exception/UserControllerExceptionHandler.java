package kr.codesqaud.cafe.account.exception;

import kr.codesqaud.cafe.account.dto.LoginForm;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice("kr.codesqaud.cafe.account")
public class UserControllerExceptionHandler {

    private static final String EMAIL_ERROR = "emailError";
    private static final String PASSWORD_ERROR = "passwordError";

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalLoginPasswordException.class)
    public String handlerIllegalLoginPasswordException(Model model, IllegalLoginPasswordException e) {
        model.addAttribute(new LoginForm());
        model.addAttribute(PASSWORD_ERROR, e.getMessage());
        return "account/loginFailed";
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchLoginEmailException.class)
    public String handlerNoSuchLoginEmailException(Model model, NoSuchLoginEmailException e) {
        model.addAttribute(new LoginForm());
        model.addAttribute(EMAIL_ERROR, e.getMessage());
        return "account/loginFailed";
    }
}
