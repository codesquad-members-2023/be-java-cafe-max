package kr.codesqaud.cafe.controller.dto;

public class ModifiedUserDTO {
    private final String userId;
    private final String originPassword;
    private final String newPassword;
    private final String name;
    private final String email;

    public ModifiedUserDTO(String userId, String originPassword, String newPassword, String name, String email) {
        this.userId = userId;
        this.originPassword = originPassword;
        this.newPassword = newPassword;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getOriginPassword() {
        return originPassword;
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
