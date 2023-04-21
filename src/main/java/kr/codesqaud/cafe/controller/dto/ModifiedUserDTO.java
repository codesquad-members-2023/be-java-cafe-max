package kr.codesqaud.cafe.controller.dto;

public class ModifiedUserDTO {
    private final String originPassword;
    private final String newPassword;
    private final String newName;
    private final String newEmail;

    public ModifiedUserDTO(String originPassword, String newPassword, String newName, String newEmail) {
        this.originPassword = originPassword;
        this.newPassword = newPassword;
        this.newName = newName;
        this.newEmail = newEmail;
    }


    public String getOriginPassword() {
        return originPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewName() {
        return newName;
    }

    public String getNewEmail() {
        return newEmail;
    }
}
