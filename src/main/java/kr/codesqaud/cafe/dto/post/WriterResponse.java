package kr.codesqaud.cafe.dto.post;

import kr.codesqaud.cafe.domain.Member;

public class WriterResponse {
    private final String writerId;
    private final String nickName;

    public WriterResponse(String writerId, String nickName) {
        this.writerId = writerId;
        this.nickName = nickName;
    }

    public String getWriterId() {
        return writerId;
    }

    public String getNickName() {
        return nickName;
    }

    public static WriterResponse from(Member member) {
        return new WriterResponse(member.getEmail(), member.getNickName());
    }
}
