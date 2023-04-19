package kr.codesqaud.cafe.exception;

public class CommentNotFoundException extends RuntimeException {
	public CommentNotFoundException() {
		super("댓글 정보를 찾을 수 없습니다.");
	}
}
