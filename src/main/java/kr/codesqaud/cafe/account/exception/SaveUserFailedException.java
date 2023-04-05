package kr.codesqaud.cafe.account.exception;

import kr.codesqaud.cafe.exception.CustomException;
import kr.codesqaud.cafe.exception.ErrorCode;

public class SaveUserFailedException extends CustomException {
	public SaveUserFailedException(ErrorCode errorCode) {
		super(errorCode);
	}

}
