package kr.codesqaud.cafe.account.exception.service;

import kr.codesqaud.cafe.account.exception.AccountException;
import kr.codesqaud.cafe.account.exception.ErrorCode;

public class InvalidIdException extends AccountException {

	public InvalidIdException(ErrorCode errorCode) {
		super(errorCode);
	}
}
