package kr.codesqaud.cafe.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error/400";
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public String handleArticleNotFound(ArticleNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error/400";
    }

    @ExceptionHandler(ReplyNotFoundException.class)
    public String handleReplyNotFound(ReplyNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error/400";
    }
}
