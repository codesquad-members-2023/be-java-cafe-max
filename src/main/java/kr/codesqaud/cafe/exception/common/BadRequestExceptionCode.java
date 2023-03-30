package kr.codesqaud.cafe.exception.common;

import java.util.Arrays;
import kr.codesqaud.cafe.exception.member.DuplicateMemberEmailException;
import kr.codesqaud.cafe.exception.member.DuplicateMemberIdException;

public enum BadRequestExceptionCode {

    ERROR_CODE_NOT_FOUND("[ERROR_NOT_FOUND]", "에러 코드를 찾을 수 없습니다.", ErrorCodeNotFoundException.class),
    DUPLICATE_MEMBER_ID("[MEMBER_001]", "아이디가 중복입니다.", DuplicateMemberIdException .class),
    DUPLICATE_MEMBER_EMAIL("[MEMBER_002]", "이메일이 중복입니다.", DuplicateMemberEmailException .class);

    private final String errorCode;
    private final String errorMessage;
    private final Class<? extends BadRequestException> classType;

    BadRequestExceptionCode(String errorCode, String errorMessage,
        Class<? extends BadRequestException> classType) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.classType = classType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static BadRequestExceptionCode getBadException(Class<? extends BadRequestException> classType) {
        return Arrays.stream(values())
            .filter(error -> error.classType.equals((classType)))
            .findFirst()
            .orElseThrow(() -> new ErrorCodeNotFoundException(null));
    }
}
