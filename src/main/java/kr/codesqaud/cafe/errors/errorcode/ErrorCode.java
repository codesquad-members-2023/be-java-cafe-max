package kr.codesqaud.cafe.errors.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String getName();

    HttpStatus getHttpStatus();

    String getMessage();

    default String getRedirectUrl() {
        return null;
    }
}
