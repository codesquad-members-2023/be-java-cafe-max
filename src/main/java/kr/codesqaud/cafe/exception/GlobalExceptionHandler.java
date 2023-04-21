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
    public String duplicateKeyExceptionHandler(HttpServletRequest request, DuplicateKeyException e, Model model) {
        model.addAttribute("userId", request.getParameter("userId"));
        model.addAttribute("password", request.getParameter("password"));
        model.addAttribute("userName", request.getParameter("userName"));
        model.addAttribute("email", request.getParameter("email"));
        model.addAttribute("error_message", e.getMessage());
        return "user/form_failed";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String resourceNotFoundException(ResourceNotFoundException e, Model model) {
        model.addAttribute("error_message", e.getMessage());
        return "error/error_page";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public String AccessDeniedOrForbiddenException(ForbiddenException e, Model model) {
        model.addAttribute("error_message", e.getMessage());
        return "error/403";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(LoginFailedException.class)
    public String failedLoginException(HttpServletRequest request, LoginFailedException e, Model model) {
        model.addAttribute("userId", request.getParameter("userId"));
        model.addAttribute("password", request.getParameter("password"));
        model.addAttribute("error_message", e.getMessage());
        return "user/login_failed";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
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
