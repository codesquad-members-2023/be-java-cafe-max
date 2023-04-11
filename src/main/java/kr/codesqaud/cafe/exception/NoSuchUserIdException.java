package kr.codesqaud.cafe.exception;

public class NoSuchUserIdException extends RuntimeException {
	public NoSuchUserIdException() {
		super("등록된 ID가 없습니다.");
	}
}
