package kr.codesqaud.cafe.exception;

public class DuplicateUserException extends RuntimeException {
	public DuplicateUserException(String message) {
		super(message);
		System.err.println("중복된 " + message + "값입니다.");
	}
}
