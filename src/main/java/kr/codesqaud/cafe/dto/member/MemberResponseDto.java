package kr.codesqaud.cafe.dto.member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.Member;

public class MemberResponseDto {
    private final Long memberId;
    private final String email;
    private final String password;
    private final String nickname;
    private final LocalDateTime createDate;

    public MemberResponseDto(Long memberId, String email, String password, String nickname, LocalDateTime createDate) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
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

    public String getNickname() {
        return nickname;
    }

    public String convertLocalDateTime() {
        return createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getMemberId(), member.getEmail(), member.getPassword(), member.getNickname(), member.getCreateDate());
    }
}
