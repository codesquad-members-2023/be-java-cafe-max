package kr.codesqaud.cafe.global;


import kr.codesqaud.cafe.exception.AlreadyUserExistenceException;
import kr.codesqaud.cafe.exception.LoginInvalidPasswordException;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import kr.codesqaud.cafe.exception.UserUpdateInvalidPasswordException;
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
     * sign-up 또는 post시 발생할수있는 BindException 처리
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
        else if(requestUri.contains("/profile")){
            return createErrorResponseModelAndView("user/updateForm",e,false);
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

    @ExceptionHandler(UserUpdateInvalidPasswordException.class)
    public ModelAndView handleInvalidPasswordException(UserUpdateInvalidPasswordException e) {
        ModelAndView mav = createErrorResponseModelAndView("user/updateForm", e, true);
        mav.addObject("id", e.getId());
        return mav;
    }

    /**
     * 로그인시 비밀번호가 틀리면 발생되는 예외다. login-form에서 존재하지 않는 id가 입력될시 error라는 메세지를 사용하고있기때문에
     * 비밀번호는 가 틀렸을땐 password-error 를 통해 login-form 에 error-message를 넘긴다.
     * @param e
     * @return
     */
    @ExceptionHandler(LoginInvalidPasswordException.class)
    public ModelAndView handleLoginInvalidPasswordException(LoginInvalidPasswordException e) {
        ModelAndView mav = createErrorResponseModelAndView("user/login", e, false);
        mav.addObject("password-error",e.getMessage());
        return mav;
    }

}
