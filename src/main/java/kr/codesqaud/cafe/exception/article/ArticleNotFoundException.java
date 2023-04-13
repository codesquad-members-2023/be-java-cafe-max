package kr.codesqaud.cafe.exception.article;

public class ArticleNotFoundException extends RuntimeException{

    private static final String ARTICLE_NOT_FOUND_EXCEPTION = "해당 글이 존재하지 않습니다.";
    public ArticleNotFoundException() {
        super(ARTICLE_NOT_FOUND_EXCEPTION);
    }
}
