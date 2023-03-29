package kr.codesqaud.cafe.exception;

import groovy.util.logging.Slf4j;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    private static final Logger logger = Logger.getLogger("ExceptionAdvice");

    // BaseException을 구현한 예외 클래스들을 처리하고 응답합니다.
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
            ex.getExceptionType().getErrorCode(),
            ex.getExceptionType().getHttpStatus(),
            ex.getExceptionType().getErrorMessage());
        logger.info(exceptionDto.toString());
        return new ResponseEntity<>(exceptionDto, exceptionDto.httpStatus);
    }

    // 서버에서 예외가 발생하더라도 상태코드 200을 반환합니다.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    static class ExceptionDto {

        private final int errorCode;
        private final HttpStatus httpStatus;
        private final String errorMessage;

        public ExceptionDto(int errorCode, HttpStatus httpStatus, String errorMessage) {
            this.errorCode = errorCode;
            this.httpStatus = httpStatus;
            this.errorMessage = errorMessage;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        @Override
        public String toString() {
            return String.format("{errorCode: %d, httpStatus: %s, errorMessage: %s}",
                errorCode, httpStatus, errorMessage);
        }
    }
}
