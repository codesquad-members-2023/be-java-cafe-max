package kr.codesqaud.cafe.global.exception.user;

public class InvalidPasswordException extends RuntimeException {
	public InvalidPasswordException() {
		super("기존 비밀번호와 일치하지 않습니다.");
	}
}
