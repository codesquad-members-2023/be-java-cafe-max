package kr.codesqaud.cafe.exception;

import kr.codesqaud.cafe.exception.common.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // todo : 콘솔에 errorMessage 안찍히는 게 맞나? 로깅을 따로 해줘야 하나?
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getErrorMessage());
        return "error/404";
    }

}
