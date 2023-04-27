package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Member {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final LocalDateTime createDateTime;

    public Member(Long id) {
        this(id, null, null, null, null);
    }
    public Member(Long id, String email) {
        this(id, email, null, null, null);
    }

    public Member(String email, String password, String nickname) {
        this(null, email, password, nickname, LocalDateTime.now());
    }

    public Member(Long id, String email, String password, String nickname) {
        this(id, email, password, nickname, null);
    }

    public Member(String email, String password, String nickname, LocalDateTime createDateTime) {
        this(null, email, password, nickname, createDateTime);
    }

    public Member(Long id, String email, String password, String nickname,
        LocalDateTime createDateTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createDateTime = createDateTime;
    }

    public static Member of(Long id, String nickname) {
        return new Member(id, null, null, nickname, null);
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

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
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
}
