package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Member {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickName;
    private final LocalDateTime createDate;

    private Member(Long id, String email, String password, String nickName,
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

    public static MemberBuilder builder() {
        return new MemberBuilder();
    }

    public static class MemberBuilder {

        private Long id;
        private String email;
        private String password;
        private String nickName;
        private LocalDateTime createDate;

        public MemberBuilder() {
        }

        public MemberBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MemberBuilder email(String email) {
            this.email = email;
            return this;
        }

        public MemberBuilder password(String password) {
            this.password = password;
            return this;
        }

        public MemberBuilder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public MemberBuilder createDate(LocalDateTime createDate) {
            this.createDate = createDate;
            return this;
        }

        public Member build() {
            return new Member(id, email, password, nickName, createDate);
        }
    }
}
