package kr.codesqaud.cafe.dto.user;

public class UserUpdateRequest {
    private String userId;
    private String currentPassword;
    private String newPassword;
    private String name;
    private String email;

    public UserUpdateRequest() {
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
