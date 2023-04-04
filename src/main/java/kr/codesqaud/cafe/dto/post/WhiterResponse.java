package kr.codesqaud.cafe.dto.post;

import kr.codesqaud.cafe.domain.Member;

public class WhiterResponse {

    private final Long id;
    private final String nickName;

    public WhiterResponse(Long id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public static WhiterResponse from(Member member) {
        return new WhiterResponse(member.getId(), member.getNickName());
    }
}
