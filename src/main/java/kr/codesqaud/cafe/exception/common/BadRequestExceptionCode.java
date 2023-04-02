package kr.codesqaud.cafe.exception.common;

import java.util.Arrays;
import kr.codesqaud.cafe.exception.member.DuplicateMemberEmailException;
import kr.codesqaud.cafe.exception.member.DuplicateMemberIdException;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.exception.post.PostNotFoundException;

public enum BadRequestExceptionCode {

    ERROR_CODE_NOT_FOUND("[ERROR_NOT_FOUND]", "에러 코드를 찾을 수 없습니다.", ErrorCodeNotFoundException.class),
    DUPLICATE_MEMBER_ID("[MEMBER_001]", "회원 아이디가 중복입니다.", DuplicateMemberIdException .class),
    DUPLICATE_MEMBER_EMAIL("[MEMBER_002]", "회원 이메일이 중복입니다.", DuplicateMemberEmailException .class),
    MEMBER_NOT_FOUND("[MEMBER_010]", "해당 회원을 찾을 수 없습니다.", MemberNotFoundException.class),
    POST_NOT_FOUND("[POST_010]", "해당 게시물을 찾을 수 없습니다.", PostNotFoundException.class);

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
