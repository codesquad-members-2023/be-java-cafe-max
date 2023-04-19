package kr.codesqaud.cafe.global.exception.user;

public class InvalidLoginInfoException extends RuntimeException {

	public InvalidLoginInfoException() {
		super("아이디 혹은 비밀번호가 일치하지 않습니다.");
	}
}
