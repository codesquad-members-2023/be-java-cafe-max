package kr.codesqaud.cafe.user.exception;

public class UserIdDuplicateException extends Exception {
	public UserIdDuplicateException(String userId) {
		super(userId + "는 이미 등록된 아이디 입니다.");
	}
}
