package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(Long id, String username, String nickname, String password, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User(String username, String name, String password, String email) {
        this(null, username, name, password, email, null, null);
    }

    public User(Long id, String nickname, String password) {
        this(id, null, nickname, password, null, null, LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNotMatchedPassword(String password) {
        return !this.password.equals(password);
    }

    public boolean isChangedUsername(String username) {
        return !this.username.equals(username);
    }
}
