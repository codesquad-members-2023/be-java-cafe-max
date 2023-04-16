package kr.codesqaud.cafe.exception.article;

/**
 *
 */
public class IllegalArticleStateException extends RuntimeException {

    public IllegalArticleStateException() {
    }

    public IllegalArticleStateException(String message) {
        super(message);
    }

    public IllegalArticleStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArticleStateException(Throwable cause) {
        super(cause);
    }
}
