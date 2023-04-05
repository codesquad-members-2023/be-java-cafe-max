package kr.codesqaud.cafe.web.exception.user;

import groovy.util.logging.Slf4j;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import kr.codesqaud.cafe.web.exception.BaseException;
import kr.codesqaud.cafe.web.exception.ExceptionDto;
import kr.codesqaud.cafe.web.exception.common.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class UserExceptionAdvice {

    private static final Logger logger = Logger.getLogger("ExceptionAdvice");

    /**
     * UserDuplicatedException : 사용자 중복 예외 처리
     * <p>
     * UserInvalidLoginException : 유효하지 않은 로그인 예외 처리
     * <p>
     * UserNotPasswordMatchingException : 패스워드 불일치 예외 처리
     */
    @ExceptionHandler({UserDuplicatedException.class, UserNotLoginMatchingException.class,
        UserNotPasswordMatchingException.class})
    public ResponseEntity<Object> handleUserDuplicatedException(BaseException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
            ex.getExceptionType().getErrorCode(),
            ex.getExceptionType().getHttpStatus(),
            ex.getExceptionType().getErrorMessage());
        logger.info(exceptionDto.toString());
        return new ResponseEntity<>(exceptionDto, exceptionDto.getHttpStatus());
    }

    // 도메인의 유효하지 않은 입력 형식에 대해서 예외 처리합니다.
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(BaseException ex,
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
            new InvalidFormatException(UserExceptionType.INVALID_USER_FORMAT);
        return handleInvalidFormatException(bex, ex);
    }

    // 사용자가 브라우저를 통하여 db에 없는 회원의 프로필을 접근하고자 하는 경우 전체 회원 목록 조회 페이지(user/list.html)로 이동하도록 처리합니다.
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException() {
        return "redirect:/users";
    }
}
