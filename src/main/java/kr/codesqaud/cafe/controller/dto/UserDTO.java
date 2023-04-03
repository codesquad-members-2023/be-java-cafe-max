package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.User;

public class UserDTO {
    private String nickName;
    private String email;
    private String password;
    private String id;

    public UserDTO(String nickName, String email, String password, String id) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User toUser(){
        return new User(nickName,email,password,id);
    }
}
