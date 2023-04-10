package kr.codesqaud.cafe;

import kr.codesqaud.cafe.exception.signUpException.InvalidUserIdException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUserIdException.class)
    public String handleInvalidUserID(InvalidUserIdException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";  // TODO: redirect 해줘야 맞는 것 같은데, redirect하면 error 메시지가 뷰에 전달되지 않고 있음
    }
}
