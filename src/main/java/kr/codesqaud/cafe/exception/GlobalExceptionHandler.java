package kr.codesqaud.cafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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
}
