package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.domain.Member;

public class MemberResponseDto {
    private final String id;
    private final String email;
    private final String password;
    private final String nickName;
    private final LocalDateTime createTime;

    public MemberResponseDto(String id, String email, String password, String nickName, LocalDateTime createTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getId(), member.getEmail(), member.getPassword(), member.getNickName(), member.getCreateTime());
    }
}
