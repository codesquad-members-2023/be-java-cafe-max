package kr.codesqaud.cafe.exception.article;

public class InvalidArticleDeletionException extends IllegalArticleStateException{

    public static final String ERROR_MESSAGE = "댓글이 있어 게시글을 삭제할 수 없습니다.";

    public InvalidArticleDeletionException() {
        super(ERROR_MESSAGE);
    }

    public InvalidArticleDeletionException(String message) {
        super(message);
    }

    public InvalidArticleDeletionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArticleDeletionException(Throwable cause) {
        super(cause);
    }
}
