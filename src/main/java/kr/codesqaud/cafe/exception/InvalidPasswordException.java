package kr.codesqaud.cafe.exception;

public class InvalidPasswordException extends RuntimeException {
	public InvalidPasswordException() {
		super("비밀번호가 다릅니다.");
	}
}
