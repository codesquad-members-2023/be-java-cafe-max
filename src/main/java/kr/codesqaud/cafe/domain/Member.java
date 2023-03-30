package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Member {

    private final String id;
    private final String email;
    private final String password;
    private final String nickName;
    private final LocalDateTime createDate;

    public Member(String id, String email, String password, String nickName,
        LocalDateTime createDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
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

    public boolean equalsId(String id) {
        return this.id.equals(id);
    }

    public boolean equalsEmail(String email) {
        return this.email.equals(email);
    }
}
