package kr.codesqaud.cafe.dto.post;

import kr.codesqaud.cafe.domain.Member;

public class WriterResponse {

    private final Long id;
    private final String nickname;

    public WriterResponse(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public static WriterResponse from(Member member) {
        return new WriterResponse(member.getId(), member.getNickname());
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}
