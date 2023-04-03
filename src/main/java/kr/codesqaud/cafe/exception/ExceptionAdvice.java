package kr.codesqaud.cafe.exception;

import groovy.util.logging.Slf4j;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import kr.codesqaud.cafe.exception.user.UserDuplicatedException;
import kr.codesqaud.cafe.exception.user.UserExceptionType;
import kr.codesqaud.cafe.exception.user.UserInvalidFormatException;
import kr.codesqaud.cafe.exception.user.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    private static final Logger logger = Logger.getLogger("ExceptionAdvice");

    // 사용자의 중복된 입력에 대해서 예외 처리합니다.
    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity<Object> handleUserDuplicatedException(BaseException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
            ex.getExceptionType().getErrorCode(),
            ex.getExceptionType().getHttpStatus(),
            ex.getExceptionType().getErrorMessage());
        logger.info(exceptionDto.toString());
        return new ResponseEntity<>(exceptionDto, exceptionDto.getHttpStatus());
    }

    // 사용자의 유효하지 않은 입력 형식에 대해서 예외 처리합니다.
    @ExceptionHandler(UserInvalidFormatException.class)
    public ResponseEntity<Object> handleUserValidFormatException(BaseException ex,
        MethodArgumentNotValidException mex) {
        // key: 필드 입력 제목(ex, name, password), value: 에러 내용을 담은 ExceptionDto 객체
        Map<String, ExceptionDto> exceptionDtoMap = new HashMap<>();
        for (FieldError error : mex.getFieldErrors()) {
            ExceptionDto exceptionDto = new ExceptionDto(
                ex.getExceptionType().getErrorCode(),
                ex.getExceptionType().getHttpStatus(),
                error.getDefaultMessage());
            exceptionDtoMap.put(error.getField(), exceptionDto);
        }
        return new ResponseEntity<>(exceptionDtoMap, ex.getExceptionType().getHttpStatus());
    }

    // 사용자의 유효하지 않은 입력 형식에 대해서 handleUserValidFormatException 메서드에게 예외처리를 전달합니다.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex) {
        BaseException bex =
            new UserInvalidFormatException(UserExceptionType.INVALID_USER_FORMAT);
        return handleUserValidFormatException(bex, ex);
    }

    // 사용자가 브라우저를 통하여 db에 없는 회원의 프로필을 접근하고자 하는 경우 전체 회원 목록 조회 페이지(user/list.html)로 이동하도록 처리합니다.
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException() {
        return "redirect:/users";
    }
}
