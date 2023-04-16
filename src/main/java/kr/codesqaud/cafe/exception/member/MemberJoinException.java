package kr.codesqaud.cafe.exception.member;

import kr.codesqaud.cafe.dto.member.MemberJoinRequestDto;

public class MemberJoinException extends RuntimeException {
    private final MemberExceptionType memberExceptionType;
    private final MemberJoinRequestDto memberJoinRequestDto;

    public MemberJoinException(MemberExceptionType memberExceptionType, MemberJoinRequestDto memberJoinRequestDto) {
        this.memberExceptionType = memberExceptionType;
        this.memberJoinRequestDto = memberJoinRequestDto;
    }

    public MemberExceptionType getMemberExceptionType() {
        return memberExceptionType;
    }

    public MemberJoinRequestDto getMemberJoinRequestDto() {
        return memberJoinRequestDto;
    }
}
