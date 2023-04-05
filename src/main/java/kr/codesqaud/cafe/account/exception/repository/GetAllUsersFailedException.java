package kr.codesqaud.cafe.account.exception.repository;

import kr.codesqaud.cafe.account.exception.AccountException;
import kr.codesqaud.cafe.account.exception.ErrorCode;

public class GetAllUsersFailedException extends AccountException {
	public GetAllUsersFailedException(ErrorCode errorCode) {
		super(errorCode);
	}

}
