package kr.codesqaud.cafe.account.exception;

public class NoSuchIdException extends AccountException {

	public NoSuchIdException(ErrorCode errorCode) {
		super(errorCode);
	}

	public NoSuchIdException(Throwable cause, ErrorCode errorCode) {
		super(cause, errorCode);
	}
}
