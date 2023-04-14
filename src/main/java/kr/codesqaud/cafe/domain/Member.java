package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Member {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickName;
    private final LocalDateTime createDate;

    public Member(Long id) {
        this.id = id;
        this.email = null;
        this.password = null;
        this.nickName = null;
        this.createDate = null;
    }

    public Member(Long id, String email, String password, String nickName,
        LocalDateTime createDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createDate = createDate;
    }

    public Long getId() {
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

    public boolean equalsId(Long id) {
        return Objects.equals(this.id, id);
    }

    public boolean equalsEmail(String email) {
        return this.email.equals(email);
    }

    public boolean equalsPassword(String password) {
        return this.password.equals(password);
    }

    public Member createWithId(Long id) {
        return new Member(id, email, password, nickName, createDate);
    }
}
