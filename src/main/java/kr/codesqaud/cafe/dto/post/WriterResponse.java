package kr.codesqaud.cafe.dto.post;

import kr.codesqaud.cafe.domain.Member;

public class WriterResponse {

    private final Long id;
    private final String nickName;

    public WriterResponse(Long id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public static WriterResponse from(Member member) {
        return new WriterResponse(member.getId(), member.getNickName());
    }
}
