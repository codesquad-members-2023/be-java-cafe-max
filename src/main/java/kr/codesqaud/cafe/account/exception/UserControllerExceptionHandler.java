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
    public static final String EMAIL_ERROR_MESSAGE = "존재하지 않는 이메일입니다";
    public static final String PASSWORD_ERROR_MESSAGE = "비밀번호가 일치하지 않습니다";

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalLoginPasswordException.class)
    public String handlerIllegalLoginPasswordException(Model model) {
        model.addAttribute(new LoginForm());
        model.addAttribute(PASSWORD_ERROR, PASSWORD_ERROR_MESSAGE);
        return "account/loginFailed";
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchLoginEmailException.class)
    public String executeRuntimeErrorHandler(Model model) {
        model.addAttribute(new LoginForm());
        model.addAttribute(EMAIL_ERROR, EMAIL_ERROR_MESSAGE);
        return "account/loginFailed";
    }
}
