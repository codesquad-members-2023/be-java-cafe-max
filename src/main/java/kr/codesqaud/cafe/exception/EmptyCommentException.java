package kr.codesqaud.cafe.exception;

public class EmptyCommentException extends RuntimeException {

    public EmptyCommentException() {
        super("댓글 내용이 없습니다");
    }
}
