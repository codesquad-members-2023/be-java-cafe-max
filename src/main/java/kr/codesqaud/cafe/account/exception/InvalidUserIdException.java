package kr.codesqaud.cafe.account.exception;

import kr.codesqaud.cafe.exception.CustomException;
import kr.codesqaud.cafe.exception.ErrorCode;

public class InvalidUserIdException extends CustomException {

	public InvalidUserIdException(ErrorCode errorCode) {
		super(errorCode);
	}
}
