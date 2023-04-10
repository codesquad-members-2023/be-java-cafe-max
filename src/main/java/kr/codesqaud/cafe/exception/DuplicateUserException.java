package kr.codesqaud.cafe.exception;

public class DuplicateUserException extends RuntimeException {
	public DuplicateUserException(String message) {
		super(message);
	}
}
