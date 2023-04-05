package kr.codesqaud.cafe.account.exception;

public class InvalidIdException extends AccountException {
	public InvalidIdException(Throwable cause, ErrorCode errorCode) {
		super(cause, errorCode);
	}
}
