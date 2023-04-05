package kr.codesqaud.cafe.account.exception.repository;

import kr.codesqaud.cafe.exception.CustomException;
import kr.codesqaud.cafe.exception.ErrorCode;

public class UpdateUserFailedException extends CustomException {
	public UpdateUserFailedException(ErrorCode errorCode) {
		super(errorCode);
	}

}
