package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String userId;
    private String name;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(Long id, String userId, String name, String password, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User(String userId, String name, String password, String email) {
        this(null, userId, name, password, email, LocalDateTime.now(), LocalDateTime.now());
    }

    public User(Long id, String userId, String password, LocalDateTime updatedAt) {
        this(id, userId, null, password, null, null, updatedAt);
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
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

    public boolean isChangedUserId(String userId) {
        return !this.userId.equals(userId);
    }
}
