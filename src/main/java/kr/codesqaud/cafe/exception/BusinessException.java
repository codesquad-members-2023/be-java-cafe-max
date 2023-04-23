package kr.codesqaud.cafe.exception;

public class BusinessException extends RuntimeException {

	public BusinessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BusinessException(final String message) {
		super(message);
	}
}
