package kr.codesqaud.cafe.user.exception;

public class UserNotExistException extends Exception {
	public UserNotExistException(String userId) {
		super(userId + "는 존재하지 않는 유저입니다.");
	}
}
