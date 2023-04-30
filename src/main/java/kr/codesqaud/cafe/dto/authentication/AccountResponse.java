package kr.codesqaud.cafe.dto.authentication;

import kr.codesqaud.cafe.domain.Member;

public class AccountResponse {

    private final Long memberId;
    private final String memberNickname;

    public AccountResponse(Long memberId, String memberNickname) {
        this.memberId = memberId;
        this.memberNickname = memberNickname;
    }

    public static AccountResponse from(Member member) {
        return new AccountResponse(member.getId(), member.getNickname());
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getMemberNickname() {
        return memberNickname;
    }
}
