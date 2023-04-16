package kr.codesqaud.cafe.exception.article;

public class InvalidRequesterIdException extends IllegalArticleStateException{

    public static final String ERROR_MESSAGE = "게시글 수정 요청 ID와 작성자가 일치하지 않습니다.";

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
