package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Member {
    private Long memberId;
    private String email;
    private final String password;
    private String nickname;
    private final LocalDateTime createDate;

    public Member(String email, String password, String nickname, LocalDateTime createDate) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createDate = createDate;
    }

    public Member(Long memberId, String email, String password, String nickname, LocalDateTime createDate) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
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

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isDifferentNickname(String nickname) {
        return !this.nickname.equals(nickname);
    }

    public boolean isNotMatchedPassword(String password) {
        return !this.password.equals(password);
    }

}
