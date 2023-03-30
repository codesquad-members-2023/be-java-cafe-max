package kr.codesqaud.cafe.dto.member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import kr.codesqaud.cafe.domain.Member;

public class MemberResponse {

    private final String id;
    private final String email;
    private final String nickName;
    private final LocalDateTime createDate;

    public MemberResponse(String id, String email, String nickName, LocalDateTime createDate) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public String getCreateDate() {
        return createDate.format(DateTimeFormatter.ISO_DATE);
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getEmail()
            , member.getNickName(), member.getCreateDate());
    }
}
