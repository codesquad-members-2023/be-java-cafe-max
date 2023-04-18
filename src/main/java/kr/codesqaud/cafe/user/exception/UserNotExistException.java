package kr.codesqaud.cafe.user.exception;

public class UserNotExistException extends Exception {
	public UserNotExistException(long id) {
		super("id=" + id + "에 해당하는 유저를 찾을 수 없습니다.");
	}

	public UserNotExistException(String userId) {
		super(userId + "는 존재하지 않는 유저입니다.");
	}
}
