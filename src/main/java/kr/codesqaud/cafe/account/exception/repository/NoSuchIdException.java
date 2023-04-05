package kr.codesqaud.cafe.account.exception.repository;

import kr.codesqaud.cafe.account.exception.AccountException;
import kr.codesqaud.cafe.account.exception.ErrorCode;

public class NoSuchIdException extends AccountException {

	public NoSuchIdException(ErrorCode errorCode) {
		super(errorCode);
	}

	public NoSuchIdException(Throwable cause, ErrorCode errorCode) {
		super(cause, errorCode);
	}
}
