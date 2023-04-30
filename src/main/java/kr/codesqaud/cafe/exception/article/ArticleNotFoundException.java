package kr.codesqaud.cafe.exception.article;

public class ArticleNotFoundException extends IllegalArticleStateException {

    public static final String ERROR_MESSAGE = "존재하지 않는 게시글입니다.";

    public ArticleNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public ArticleNotFoundException(String message) {
        super(message);
    }

    public ArticleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleNotFoundException(Throwable cause) {
        super(cause);
    }
}
