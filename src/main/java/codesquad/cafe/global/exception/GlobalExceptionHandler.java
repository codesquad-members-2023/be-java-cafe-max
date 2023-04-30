package codesquad.cafe.global.exception;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;


@ControllerAdvice
public class GlobalExceptionHandler extends DefaultHandlerExceptionResolver {

    @ExceptionHandler(BindException.class)
    protected ModelAndView handleBindException(BindException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/400");
        modelAndView.addObject("message", e.getBindingResult().getFieldError().getDefaultMessage());
        return modelAndView;
    }

    @ExceptionHandler(CustomException.class)
    public ModelAndView handleCustomException(CustomException e) {
        ModelAndView modelAndView = new ModelAndView();
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        modelAndView.setViewName(errorResponse.getViewName());
        modelAndView.addObject("message",errorResponse.getMessage());
        return modelAndView;
    }
}
