package kr.codesqaud.cafe.account.exception.repository;

import kr.codesqaud.cafe.account.exception.AccountException;
import kr.codesqaud.cafe.account.exception.ErrorCode;

public class NoSuchEmailException extends AccountException {

	public NoSuchEmailException(ErrorCode errorCode) {
		super(errorCode);
	}

	public NoSuchEmailException(Throwable cause, ErrorCode errorCode) {
		super(cause, errorCode);
	}
}
