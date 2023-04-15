package kr.codesqaud.cafe.exception.member;

import kr.codesqaud.cafe.dto.member.MemberLoginRequestDto;

public class MemberLoginException extends RuntimeException {
    private final MemberExceptionType memberExceptionType;
    private final MemberLoginRequestDto memberLoginRequestDto;

    public MemberLoginException(MemberExceptionType memberExceptionType, MemberLoginRequestDto memberLoginRequestDto) {
        this.memberExceptionType = memberExceptionType;
        this.memberLoginRequestDto = memberLoginRequestDto;
    }

    public MemberExceptionType getMemberExceptionType() {
        return memberExceptionType;
    }

    public MemberLoginRequestDto getMemberLoginRequestDto() {
        return memberLoginRequestDto;
    }
}
