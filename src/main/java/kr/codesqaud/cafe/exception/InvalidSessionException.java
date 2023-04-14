package kr.codesqaud.cafe.exception;

public class InvalidSessionException extends RuntimeException {
	public InvalidSessionException() {
		super("존재하지 않는 세션이거나, 유효하지 않은 세션입니다.");
	}
}
