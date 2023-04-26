package kr.codesqaud.cafe.exception;

public class DeniedCommentModificationException extends RuntimeException {
	public DeniedCommentModificationException() {
		super("다른 사람의 댓글은 삭제 및 수정할 수 없습니다.");
	}
}
