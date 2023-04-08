package kr.codesqaud.cafe.dto.post;

import kr.codesqaud.cafe.domain.Member;

public class WriterResponse {
    private final Long writerId;
    private final String nickName;

    public WriterResponse(Long writerId, String nickName) {
        this.writerId = writerId;
        this.nickName = nickName;
    }

    public Long getWriterId() {
        return writerId;
    }

    public String getNickName() {
        return nickName;
    }

    public static WriterResponse from(Member member) {
        return new WriterResponse(member.getMemberId(), member.getNickName());
    }
}
