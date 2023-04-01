package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.User;

public class UserSignUpDTO {
    private String nickName;
    private String email;
    private String password;

    public UserSignUpDTO(String nickName, String email, String password) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
    }

    public User toUser(){
        return new User(nickName,email,password);
    }
}
