package kr.codesqaud.cafe.global;


import kr.codesqaud.cafe.exception.AlreadyUserExistenceException;
import kr.codesqaud.cafe.exception.InvalidPasswordException;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ModelAndView createErrorResponseModelAndView(String viewName, Exception e, boolean includeMessage) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        ModelAndView mav = new ModelAndView(viewName);
        if (includeMessage) {
            mav.addObject("error", errorResponse);
        }
        return mav;
    }

    /**
     * 여러군데에서 발생할수 있는 BindException 처리
     *
     * @param e
     * @param request
     * @return ModelAndView
     */
    @ExceptionHandler(BindException.class)
    public ModelAndView handleBindException(BindException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        if (requestUri.contains("/sign-up")) {
            return createErrorResponseModelAndView("user/form", e, false);
        }
        return createErrorResponseModelAndView("post/form", e, false);

    }

    @ExceptionHandler(AlreadyUserExistenceException.class)
    public ModelAndView handleAlreadyUserExistenceException(AlreadyUserExistenceException e) {
        return createErrorResponseModelAndView("user/form", e, true);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UserNotFoundException e,HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        if(requestUri.contains("/profile")){
            return createErrorResponseModelAndView("error/error", e, true);
        }
        return createErrorResponseModelAndView("user/login",e,true);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ModelAndView handleInvalidPasswordException(InvalidPasswordException e) {
        ModelAndView mav = createErrorResponseModelAndView("user/updateForm", e, true);
        mav.addObject("id", e.getId());
        return mav;
    }

}
