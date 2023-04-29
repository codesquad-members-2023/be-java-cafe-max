package kr.codesqaud.cafe.errors.handler;

import kr.codesqaud.cafe.errors.exception.PermissionDeniedException;
import kr.codesqaud.cafe.errors.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(NoHandlerFoundException e, Model model) {
        log.info("handle 404 : {}", e.toString());
        model.addAttribute("message", "페이지를 찾을 수 없습니다.");
        return "error/404";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException e, Model model) {
        log.info("handleResourceNotFoundException handling : {}", e.toString());
        model.addAttribute("message", e.getErrorCode().getMessage());
        return new ModelAndView("error/404");
    }

    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handle403(PermissionDeniedException e, Model model) {
        log.info("handle 403 : {}", e.toString());
        model.addAttribute("message", e.getErrorCode().getMessage());
        return "error/403";
    }

}
