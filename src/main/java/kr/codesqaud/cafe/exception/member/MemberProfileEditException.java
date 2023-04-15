package kr.codesqaud.cafe.exception.member;

import kr.codesqaud.cafe.dto.member.ProfileEditRequestDto;

public class MemberProfileEditException extends RuntimeException {
    private final MemberExceptionType memberExceptionType;
    private final ProfileEditRequestDto profileEditRequestDto;

    public MemberProfileEditException(MemberExceptionType memberExceptionType, ProfileEditRequestDto profileEditRequestDto) {
        this.memberExceptionType = memberExceptionType;
        this.profileEditRequestDto = profileEditRequestDto;
    }

    public MemberExceptionType getMemberExceptionType() {
        return memberExceptionType;
    }

    public ProfileEditRequestDto getProfileEditRequestDto() {
        return profileEditRequestDto;
    }
}
