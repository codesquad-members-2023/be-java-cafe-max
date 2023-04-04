package kr.codesqaud.cafe.account.exception;

public class AccountException extends RuntimeException {

	private final ErrorCode errorCode;

	public AccountException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}
}
