package kr.codesqaud.cafe.account.exception;

import kr.codesqaud.cafe.account.dto.LoginForm;
import kr.codesqaud.cafe.account.dto.ProfileEditForm;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice("kr.codesqaud.cafe.account")
public class UserControllerExceptionHandler {

    private static final String EMAIL_ERROR = "emailError";
    private static final String PASSWORD_ERROR = "passwordError";
    private static final String EMAIL = "email";
    private static final String NICK_NAME = "nickname";
    private static final String EMPTY_PASSWORD = "";

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

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalEditEmailException.class)
    public String handlerIllegalEditEmailException(HttpServletRequest request, Model model, IllegalEditEmailException e) {
        addProfileEditForm(request, model);
        model.addAttribute(EMAIL_ERROR, e.getMessage());
        return "account/profileEditFormFailed";
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalEditPasswordException.class)
    public String handlerIllegalEditPasswordException(HttpServletRequest request, Model model, IllegalEditPasswordException e) {
        addProfileEditForm(request, model);
        model.addAttribute(PASSWORD_ERROR, e.getMessage());
        return "account/profileEditFormFailed";
    }

    private static void addProfileEditForm(HttpServletRequest request, Model model) {
        String email = request.getParameter(EMAIL);
        String nickName = request.getParameter(NICK_NAME);
        model.addAttribute(new ProfileEditForm(nickName, email, EMPTY_PASSWORD));
    }
}
