package kr.codesqaud.cafe;

import kr.codesqaud.cafe.exception.article.ArticleNotFoundException;
import kr.codesqaud.cafe.exception.article.InvalidArticleDeletionException;
import kr.codesqaud.cafe.exception.article.InvalidRequesterIdException;
import kr.codesqaud.cafe.exception.signUpException.InvalidUserIdException;
import kr.codesqaud.cafe.exception.user.UserNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUserIdException.class)
    public String handleInvalidUserId(InvalidUserIdException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";  // TODO: redirect 해줘야 맞는 것 같은데, redirect하면 error 메시지가 뷰에 전달되지 않고 있음
    }

    @ExceptionHandler(InvalidRequesterIdException.class)
    public String handleInvalidRequesterId(InvalidRequesterIdException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }

    @ExceptionHandler(InvalidArticleDeletionException.class)
    public String handleInvalidDeletion(InvalidArticleDeletionException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUnregisteredId(UserNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public String handleInvalidArticle(ArticleNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
