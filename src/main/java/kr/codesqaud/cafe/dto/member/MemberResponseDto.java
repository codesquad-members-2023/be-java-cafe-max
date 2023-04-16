package kr.codesqaud.cafe.dto.member;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.domain.Member;

public class MemberResponseDto {
    private final Long memberId;
    private final String email;
    private final String password;
    private final String nickName;
    private final LocalDateTime createDate;

    public MemberResponseDto(Long memberId, String email, String password, String nickName, LocalDateTime createDate) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createDate = createDate;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }


    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getMemberId(), member.getEmail(), member.getPassword(), member.getNickName(), member.getCreateDate());
    }
}
