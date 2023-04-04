package kr.codesqaud.cafe.account.exception;

public class AccountException extends RuntimeException {

	private ErrorCode errorCode;

	public AccountException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}
}
