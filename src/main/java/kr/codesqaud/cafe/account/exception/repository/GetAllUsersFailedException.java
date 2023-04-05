package kr.codesqaud.cafe.account.exception.repository;

import kr.codesqaud.cafe.exception.CustomException;
import kr.codesqaud.cafe.exception.ErrorCode;

public class GetAllUsersFailedException extends CustomException {
	public GetAllUsersFailedException(ErrorCode errorCode) {
		super(errorCode);
	}

}
