package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Member {
    private Long memberId;
    private String email;
    private final String password;
    private String nickName;
    private final LocalDateTime createDate;

    public Member(String email, String password, String nickName, LocalDateTime createDate) {

        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createDate = createDate;
    }

    public Member(Long memberId, String email, String password, String nickName, LocalDateTime createDate) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createDate = createDate;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}
