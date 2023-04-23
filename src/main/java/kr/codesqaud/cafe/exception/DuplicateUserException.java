package kr.codesqaud.cafe.exception;

public class DuplicateUserException extends RuntimeException {
	public DuplicateUserException(String message) {
		super("이미 존재하는 회원 " + message + "입니다.");
	}
}
