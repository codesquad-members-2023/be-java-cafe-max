package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.exception.DeniedAccessException;
import kr.codesqaud.cafe.exception.DuplicatedIdException;
import kr.codesqaud.cafe.exception.LoginFailedException;
import kr.codesqaud.cafe.exception.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoginFailedException.class)
    public String loginFailedPage(LoginFailedException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "user/login_failed";
    }


    @ExceptionHandler(DeniedAccessException.class)
    public String deniedAccessPage(DeniedAccessException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "user/error";
    }

    @ExceptionHandler(BindException.class)
    public String BindException(BindException e, RedirectAttributes model, HttpServletRequest request) {
        List<FieldError> fieldError = e.getBindingResult().getFieldErrors();
        String uri = request.getRequestURI();
        String[] uriSplit = uri.split("/");
        for (int i = 0; i < e.getBindingResult().getAllErrors().size(); i++) {
            model.addFlashAttribute(fieldError.get(i).getField(), (fieldError.get(i).getDefaultMessage()));
        }
        if (uriSplit[2].equals("create")) {
            return "redirect:/user/signup";
        } else if (uriSplit[2].equals("write")) {
            return "redirect:/article/question";
        } else if ((uriSplit[1] + uriSplit[2]).equals("articleupdate")) {
            return "redirect:/article/update/" + uri.split("/")[3];
        } else {
            return "redirect:/user/update/" + uri.split("/")[3];
        }
    }

    @ExceptionHandler(NotFoundException.class)
    public String NotFoundException(NotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "user/error";
    }

    @ExceptionHandler(DuplicatedIdException.class)
    public String DuplicatedIdException(DuplicatedIdException e, RedirectAttributes model, HttpServletRequest request) {
        String uri = request.getRequestURI();
        String[] uriSplit = uri.split("/");
        model.addFlashAttribute("idError", e.getMessage());
        if (uriSplit[1].equals("signup")) {
            return "redirect:/user/signup";
        } else {
            return "redirect:/user/update/" + uri.split("/")[3];
        }
    }

}
