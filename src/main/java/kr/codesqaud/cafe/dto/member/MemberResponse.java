package kr.codesqaud.cafe.dto.member;

import java.time.LocalDateTime;
import kr.codesqaud.cafe.domain.Member;

public class MemberResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final LocalDateTime createDate;

    public MemberResponse(Long id, String email, String nickname, LocalDateTime createDate) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.createDate = createDate;
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getEmail()
            , member.getNickname(), member.getCreateDate());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
