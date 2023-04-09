package kr.codesqaud.cafe.dto.user;

import kr.codesqaud.cafe.domain.User;

public class UserUpdateRequest {
    private String userId;
    private String currentPassword;
    private String newPassword;
    private String name;
    private String email;

    public UserUpdateRequest() {
    }

    private UserUpdateRequest(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public static UserUpdateRequest from(User user) { // Entity → DTO
        return new UserUpdateRequest(user);
    }

    public User toUser() { // DTO → Entity
        return new User(this.userId, this.newPassword, this.name, this.email);
    }

    public String getUserId() { return userId; }
    public String getCurrentPassword() {
        return currentPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    public void setUserId(String userId) { this.userId = userId; }
    public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}
