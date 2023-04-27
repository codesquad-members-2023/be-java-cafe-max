package kr.codesqaud.cafe.question_comment.exception;

public class CommentNotExistException extends Exception {
	public CommentNotExistException(long id) {
		super("id=" + id + "에 해당하는 댓글을 찾을 수 없습니다.");
	}
}
