package kr.codesquad.cafe.comment;

public class UnauthorizedDeleteCommentException extends RuntimeException {

    private static final String MESSAGE = "권한이 없어 삭제할 수 없는 댓글입니다.";

    public UnauthorizedDeleteCommentException() {
        super(MESSAGE);
    }
}
