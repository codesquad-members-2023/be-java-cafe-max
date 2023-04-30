package kr.codesqaud.cafe.exception;

public class OtherCommentExistsException extends RuntimeException {
	public OtherCommentExistsException() {
		super("다른 사람의 댓글이 있으면 게시글을 삭제할 수 없습니다.");
	}
}
