package kr.codesqaud.cafe.exception.article;

public class InvalidRequesterIdException extends IllegalArticleStateException{

    public static final String ERROR_MESSAGE = "게시글 작성자가 아닙니다.";

    public InvalidRequesterIdException() {
        super(ERROR_MESSAGE);
    }

    public InvalidRequesterIdException(String message) {
        super(message);
    }

    public InvalidRequesterIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequesterIdException(Throwable cause) {
        super(cause);
    }
}
