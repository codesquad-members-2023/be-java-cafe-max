package kr.codesqaud.cafe.user.exception;

public class UserDoesNotMatchException extends Exception {
	public UserDoesNotMatchException() {
		super("아이디 또는 비밀번호를 잘못 입력했습니다.");
	}
}
