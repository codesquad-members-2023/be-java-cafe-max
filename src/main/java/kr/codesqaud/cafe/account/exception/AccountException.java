package kr.codesqaud.cafe.account.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AccountException extends RuntimeException {

	private static final Logger logger = LoggerFactory.getLogger(AccountException.class);

	private final ErrorCode errorCode;

	public AccountException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public AccountException(Throwable cause, ErrorCode errorCode) {
		super(errorCode.getMessage());
		logger.error(cause.getMessage());
		logger.error(cause.getClass().getName());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}
}
