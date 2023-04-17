package kr.codesqaud.cafe.dto.post;

import kr.codesqaud.cafe.domain.Member;

public class WriterResponse {
    private final String writerEmail;
    private final String nickName;

    public WriterResponse(String writerEmail, String nickName) {
        this.writerEmail = writerEmail;
        this.nickName = nickName;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public String getNickName() {
        return nickName;
    }

    public static WriterResponse from(Member member) {
        return new WriterResponse(member.getEmail(), member.getNickName());
    }
}
