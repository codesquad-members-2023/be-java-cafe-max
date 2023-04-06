package kr.codesqaud.cafe.global;


import kr.codesqaud.cafe.exception.IdDuplicatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdDuplicatedException.class)
    public ModelAndView handleIdDuplicatedException(IdDuplicatedException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        ModelAndView mav = new ModelAndView("/user/form");
        mav.addObject("error", errorResponse);
        return mav;
    }
}
