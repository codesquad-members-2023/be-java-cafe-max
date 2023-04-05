package kr.codesqaud.cafe.account.exception.repository;

import kr.codesqaud.cafe.account.exception.AccountException;
import kr.codesqaud.cafe.account.exception.ErrorCode;

public class UpdateUserFailedException extends AccountException {
	public UpdateUserFailedException(ErrorCode errorCode) {
		super(errorCode);
	}

}
