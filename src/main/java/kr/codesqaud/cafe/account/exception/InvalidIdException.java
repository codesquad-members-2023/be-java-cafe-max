package kr.codesqaud.cafe.account.exception;

public class InvalidIdException extends AccountException {
	public InvalidIdException(ErrorCode errorCode) {
		super(errorCode);
	}

	public InvalidIdException(Throwable cause, ErrorCode errorCode) {
		super(cause, errorCode);
	}
}
