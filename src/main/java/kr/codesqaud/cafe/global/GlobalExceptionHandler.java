package kr.codesqaud.cafe.global;


import kr.codesqaud.cafe.exception.IdDuplicatedException;
import kr.codesqaud.cafe.exception.InvalidPasswordException;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ModelAndView createErrorResponseModelAndView(String viewName, Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("error", errorResponse);
        return mav;
    }

    @ExceptionHandler(IdDuplicatedException.class)
    public ModelAndView handleIdDuplicatedException(IdDuplicatedException e) {
        return createErrorResponseModelAndView("/user/form", e);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UserNotFoundException e) {
        return createErrorResponseModelAndView("/error/error", e);
    }



}
