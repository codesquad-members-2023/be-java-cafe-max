package kr.codesqaud.cafe.web.exception.article;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ArticleExceptionAdvice {

    // 게시글을 찾지 못한 경우 게시글 목록 조회(/) 페이지로 리다이렉트 됩니다.
    @ExceptionHandler(ArticleNotFoundException.class)
    public String handleArticleNotFoundException() {
        return "redirect:/";
    }
}
