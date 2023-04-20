package kr.codesquad.cafe.post.exception;

public class DeletionFailedException extends RuntimeException {
    private static final String MESSAGE = "다른 멤버가 작성한 댓글이 있어서 삭제할 수 없습니다.";

    public DeletionFailedException() {
        super(MESSAGE);
    }
}
