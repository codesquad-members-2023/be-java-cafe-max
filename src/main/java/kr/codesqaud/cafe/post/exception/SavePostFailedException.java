package kr.codesqaud.cafe.post.exception;

import kr.codesqaud.cafe.exception.CustomException;
import kr.codesqaud.cafe.exception.ErrorCode;

public class SavePostFailedException extends CustomException {
    public SavePostFailedException(ErrorCode errorCode) {
        super(errorCode);
    }

}
