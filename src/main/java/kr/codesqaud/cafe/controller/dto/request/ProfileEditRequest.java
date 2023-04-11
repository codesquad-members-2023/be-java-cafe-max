package kr.codesqaud.cafe.controller.dto.request;

public class ProfileEditRequest {

    private final String originalPassword;
    private final String newPassword;
    private final String newUserName;
    private final String newUserEmail;

    public ProfileEditRequest(String originalPassword, String newPassword, String newUserName, String newUserEmail) {
        this.originalPassword = originalPassword;
        this.newPassword = newPassword;
        this.newUserName = newUserName;
        this.newUserEmail = newUserEmail;
    }

    public String getOriginalPassword() {
        return originalPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewUserName() {
        return newUserName;
    }

    public String getNewUserEmail() {
        return newUserEmail;
    }
}