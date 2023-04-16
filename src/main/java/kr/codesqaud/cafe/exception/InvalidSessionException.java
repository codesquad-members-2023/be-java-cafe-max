package kr.codesqaud.cafe.exception;

public class InvalidSessionException extends RuntimeException {

	public InvalidSessionException(Throwable cause) {
		super("세션이 유효하지 않습니다.", cause);
	}
}
