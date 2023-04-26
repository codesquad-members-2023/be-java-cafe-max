package kr.codesqaud.cafe.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException() {
		super("유저 아이디를 찾을 수 없습니다.");
	}
}
