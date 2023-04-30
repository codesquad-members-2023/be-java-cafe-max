package kr.codesqaud.cafe.dto.comment;

import kr.codesqaud.cafe.domain.Member;

public class WriterRequest {

    private final Long id;
    private final String nickname;

    public WriterRequest(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public Member toMember() {
        return new Member(id, nickname);
    }
}
