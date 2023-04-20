package kr.codesqaud.cafe.exception.comment;

import kr.codesqaud.cafe.exception.common.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ApiUnauthorizedException extends ApiException {

    private static final String ERROR_MESSAGE = "권환이 없습니다.";

    public ApiUnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED.value(), ERROR_MESSAGE);
    }
}
