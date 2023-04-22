package kr.codesqaud.cafe.question.exception;

public class QuestionNotExistException extends Exception {
	public QuestionNotExistException(long id) {
		super("id=" + id + "에 해당하는 게시글을 찾을 수 없습니다.");
	}
}
