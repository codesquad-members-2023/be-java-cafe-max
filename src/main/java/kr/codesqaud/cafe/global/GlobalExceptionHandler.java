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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ModelAndView createErrorResponseModelAndView(String viewName, Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("error", errorResponse);
        return mav;
    }

    private String handleExceptionWithRedirect(Exception e, String redirectUri, String attributeName, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(attributeName, e.getMessage());
        return "redirect:" + redirectUri;
    }

    /**
     * @Valid에 의해 발생할수있는 3가지 BindException 경우에대한 예외처리 메서드입니다.
     * @param request
     * @param redirectAttributes
     * @return String
     */
    @ExceptionHandler(BindException.class)
    public String handleBindException(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String requestUri = request.getRequestURI();
        if (requestUri.contains("/sign-up")) {
            return "redirect:/user/sign-up-form";
        } else if (requestUri.contains("/profile")) {
            redirectAttributes.addAttribute("id", request.getParameter("id"));
            return "redirect:/user/profile/{id}/form";
        }
        return "redirect:/article";
    }

    @ExceptionHandler(AlreadyUserExistenceException.class)
    public ModelAndView handleAlreadyUserExistenceException(AlreadyUserExistenceException e, RedirectAttributes redirectAttributes) {
        return new ModelAndView(handleExceptionWithRedirect(e, "/user/sign-up-form", "id-error", redirectAttributes));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UserNotFoundException e, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String requestUri = request.getRequestURI();
        if (requestUri.contains("/profile")) {
            return createErrorResponseModelAndView("error/error", e);
        }
        return new ModelAndView(handleExceptionWithRedirect(e, "/user/sign-in", "id-error", redirectAttributes));
    }

    @ExceptionHandler(UserUpdateInvalidPasswordException.class)
    public ModelAndView handleUserUpdateInvalidPasswordException(UserUpdateInvalidPasswordException e, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(handleExceptionWithRedirect(e, "/user/profile/" + request.getParameter("id") + "/form", "password-error", redirectAttributes));
        return mav;
    }

    @ExceptionHandler(LoginInvalidPasswordException.class)
    public ModelAndView handleLoginInvalidPasswordException(LoginInvalidPasswordException e, RedirectAttributes redirectAttributes) {
        return new ModelAndView(handleExceptionWithRedirect(e, "/user/sign-in", "password-error", redirectAttributes));
    }

}
