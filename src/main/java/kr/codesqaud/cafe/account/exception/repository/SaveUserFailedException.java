package kr.codesqaud.cafe.account.exception.repository;

import kr.codesqaud.cafe.account.exception.AccountException;
import kr.codesqaud.cafe.account.exception.ErrorCode;

public class SaveUserFailedException extends AccountException {
	public SaveUserFailedException(ErrorCode errorCode) {
		super(errorCode);
	}

	public SaveUserFailedException(Throwable cause, ErrorCode errorCode) {
		super(cause, errorCode);
	}
}
