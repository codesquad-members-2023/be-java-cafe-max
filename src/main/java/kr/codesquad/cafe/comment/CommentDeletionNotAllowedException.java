package kr.codesquad.cafe.comment;

public class CommentDeletionNotAllowedException extends RuntimeException {

    private static final String MESSAGE = "삭제할 권한이 없는 댓글입니다.";

    public CommentDeletionNotAllowedException() {
        super(MESSAGE);
    }
}
