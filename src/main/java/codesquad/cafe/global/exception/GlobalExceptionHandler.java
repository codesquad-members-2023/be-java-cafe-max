package codesquad.cafe.global.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ModelAndView handleCustomException(CustomException e) {
        ModelAndView modelAndView = new ModelAndView();
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        modelAndView.setViewName(errorResponse.getViewName());
        modelAndView.addObject(errorResponse);
        return modelAndView;
    }
}
