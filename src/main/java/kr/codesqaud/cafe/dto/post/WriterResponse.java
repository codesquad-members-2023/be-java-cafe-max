package kr.codesqaud.cafe.dto.post;

import kr.codesqaud.cafe.domain.Member;

public class WriterResponse {
    private final String writerEmail;
    private final String nickname;

    public WriterResponse(String writerEmail, String nickname) {
        this.writerEmail = writerEmail;
        this.nickname = nickname;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public String getNickname() {
        return nickname;
    }

    public static WriterResponse from(Member member) {
        return new WriterResponse(member.getEmail(), member.getNickname());
    }
}
