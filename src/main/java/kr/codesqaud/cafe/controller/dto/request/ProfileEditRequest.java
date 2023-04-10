package kr.codesqaud.cafe.controller.dto.request;

public class ProfileEditRequest {

    private final String originalPassword;
    private final String newPassword;
    private final String userName;
    private final String userEmail;

    public ProfileEditRequest(String originalPassword, String newPassword, String userName, String userEmail) {
        this.originalPassword = originalPassword;
        this.newPassword = newPassword;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getOriginalPassword() {
        return originalPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}