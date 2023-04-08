package kr.codesqaud.cafe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String ERROR_DETAILS_FORMAT = "[ ErrorStatus : %s ][ErrorCode : %s][ ErrorMessage : %s ][ RequestURL : %s ]";
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    public static final String ERROR_CODE = "errorCode";

    @ExceptionHandler(CustomException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        loggerError(request, errorCode);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setStatus(errorCode.getStatus());
        modelAndView.addObject(ERROR_CODE, errorCode);
        modelAndView.setViewName(errorCode.getViewName());
        return modelAndView;
    }

    private static void loggerError(HttpServletRequest request, ErrorCode errorCode) {
        logger.error(
                String.format(ERROR_DETAILS_FORMAT,
                        errorCode.getStatus().toString(),
                        errorCode.getCode(),
                        errorCode.getMessage(),
                        request.getRequestURI()));
    }
}
