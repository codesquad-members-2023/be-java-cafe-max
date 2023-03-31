package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.User;

public class ProfileEditDTO {
    private String nickName;
    private String email;
    private String newPassword;
    private String oriPassword;

    public ProfileEditDTO(String nickName, String email, String newPassword, String oriPassword) {
        this.nickName = nickName;
        this.email = email;
        this.newPassword = newPassword;
        this.oriPassword = oriPassword;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOriPassword() {
        return oriPassword;
    }

    public void setOriPassword(String oriPassword) {
        this.oriPassword = oriPassword;
    }

    public User toUser(int id){
        return new User(nickName,email,newPassword,id);
    }
}
