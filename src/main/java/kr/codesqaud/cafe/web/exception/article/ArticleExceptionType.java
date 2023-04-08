package kr.codesqaud.cafe.web.exception.article;

import kr.codesqaud.cafe.web.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum ArticleExceptionType implements BaseExceptionType {
    NOT_FOUND_USER(800, HttpStatus.OK, "게시물을 찾을 수 없습니다.");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    ArticleExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
