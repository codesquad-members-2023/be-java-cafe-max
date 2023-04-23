package kr.codesqaud.cafe.errors.handler;


import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.errors.errorcode.CommonErrorCode;
import kr.codesqaud.cafe.errors.errorcode.ErrorCode;
import kr.codesqaud.cafe.errors.exception.RestApiException;
import kr.codesqaud.cafe.errors.response.ErrorResponse;
import kr.codesqaud.cafe.errors.response.ErrorResponse.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleUserException(RestApiException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        logger.warn("handleMethodArgumentNotValid", e);
        CommonErrorCode errorCode = CommonErrorCode.INVALID_INPUT_FORMAT;
        return handleExceptionInternal(e, errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return new ErrorResponse(
            errorCode.getName(),
            errorCode.getHttpStatus(),
            errorCode.getMessage(),
            null);
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(e, errorCode));
    }

    private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
        List<ErrorResponse.ValidationError> validationErrorList =
            e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::of)
                .collect(Collectors.toUnmodifiableList());
        return new ErrorResponse(
            errorCode.getName(),
            errorCode.getHttpStatus(),
            errorCode.getMessage(),
            validationErrorList);
    }
}
