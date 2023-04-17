package kr.codesqaud.cafe.account;


import kr.codesqaud.cafe.post.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "account")
public class User {

    private static final String BLANK = "";

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;
    private String nickname;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.nickname = builder.nickname;
        this.role = builder.role;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isSamePassword(String targetPassword) {
        return targetPassword.equals(password);
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
