package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.User;

public class ProfileEditDTO {

    private String id;
    private String nickName;
    private String email;
    private String newPassword;
    private String oriPassword;

    public ProfileEditDTO(String nickName, String email, String newPassword, String oriPassword,String id) {
        this.nickName = nickName;
        this.email = email;
        this.newPassword = newPassword;
        this.oriPassword = oriPassword;
        this.id = id;
    }
    public String getNewPassword() {
        return newPassword;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getOriPassword() {
        return oriPassword;
    }

    public String getId() {
        return id;
    }

    public User toUser(){
        return new User(nickName,email,newPassword,id);
    }
}
