package kr.codesqaud.cafe.exception;

public class NoAuthorizationException extends RuntimeException {

	public NoAuthorizationException() {
		super("해당 리소스에 접근할 수 있는 권한이 없습니다.");
	}
}
