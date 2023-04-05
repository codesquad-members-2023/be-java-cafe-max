package kr.codesqaud.cafe.account.exception;

public abstract class AccountException extends RuntimeException {

	private final ErrorCode errorCode;

	public AccountException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}
}
