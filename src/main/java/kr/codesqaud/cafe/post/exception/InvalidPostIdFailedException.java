package kr.codesqaud.cafe.post.exception;

import kr.codesqaud.cafe.exception.CustomException;
import kr.codesqaud.cafe.exception.ErrorCode;

public class InvalidPostIdFailedException extends CustomException {
	public InvalidPostIdFailedException(ErrorCode errorCode) {
		super(errorCode);
	}

}
