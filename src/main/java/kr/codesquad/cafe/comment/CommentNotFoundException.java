package kr.codesquad.cafe.comment;

public class CommentNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당 댓글이 존재하지 않습니다.";

    public CommentNotFoundException() {
        super(MESSAGE);
    }
}
