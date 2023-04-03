package kr.codesqaud.cafe.dto.user;

public class UserUpdateRequest {
    private String userId;
    private String currentPassword;
    private String newPassword;
    private String name;
    private String email;

    public UserUpdateRequest(String userId, String currentPassword, String newPassword, String name, String email) {
        this.userId = userId;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.name = name;
        this.email = email;
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
}
