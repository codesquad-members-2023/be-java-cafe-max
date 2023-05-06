package kr.codesquad.cafe.user.domain;


import kr.codesquad.cafe.post.Post;
import org.jasypt.encryption.StringEncryptor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    private static final String BLANK = "";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private final List<Post> posts = new ArrayList<>();

    public User() {
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.nickname = builder.nickname;
        this.role = builder.role;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean isSamePassword(StringEncryptor encryptor, String targetPassword) {
        return encryptor.decrypt(password).equals(targetPassword);
    }

    public boolean isSameEmail(String email) {
        return this.email.equals(email);
    }

    public boolean isSameId(Long userId) {
        return Objects.equals(id, userId);
    }

    public boolean isManager() {
        return role.equals(Role.MANAGER);
    }

    public User setProfile(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
        return this;
    }

    public static class Builder {
        private Long id = Long.MIN_VALUE;
        private String nickname = BLANK;
        private String email = BLANK;
        private String password = BLANK;
        private Role role = Role.USER;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }


        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
