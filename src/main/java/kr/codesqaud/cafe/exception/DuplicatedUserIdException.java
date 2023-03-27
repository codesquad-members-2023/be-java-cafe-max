package kr.codesqaud.cafe.exception;

public class DuplicatedUserIdException extends RuntimeException {

	public DuplicatedUserIdException(final String message) {
		super(message);
	}
}
