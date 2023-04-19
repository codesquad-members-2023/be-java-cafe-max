package kr.codesqaud.cafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateKeyException.class)
    public String duplicateKeyExceptionHandler() {
        return "user/form_failed";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String resourceNotFoundException(ResourceNotFoundException e, Model model) {
        model.addAttribute("error_message", e.getMessage());
        return "error/error_page";
    }

    @ExceptionHandler(LoginFailedException.class)
    public String failedLoginException(HttpServletRequest request, LoginFailedException e, Model model) {
        model.addAttribute("userId", request.getParameter("userId"));
        model.addAttribute("password", request.getParameter("password"));
        model.addAttribute("error_message", e.getMessage());
        return "user/login_failed";
    }

    @ExceptionHandler(UserUpdateFailedException.class)
    public String userUpdateFailedException(HttpServletRequest request, UserUpdateFailedException e, Model model) {
        Map<String, String> userInput = new HashMap<>();
        userInput.put("userId", request.getParameter("userId"));
        userInput.put("password", request.getParameter("password"));
        userInput.put("userName", request.getParameter("userName"));
        userInput.put("email", request.getParameter("email"));
        model.addAttribute("user", userInput);

        String errorType = e.getErrorType();
        if (errorType.equals("password")) {
            model.addAttribute("error_password", true);
        } else {
            model.addAttribute("error_duplicateName", true);
        }

        return "user/update";
    }
}
