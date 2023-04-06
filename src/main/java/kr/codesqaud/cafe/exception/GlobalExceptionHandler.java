package kr.codesqaud.cafe.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    public String duplicateKeyExceptionHandler() {
        return "/user/form_failed";
    }
}
