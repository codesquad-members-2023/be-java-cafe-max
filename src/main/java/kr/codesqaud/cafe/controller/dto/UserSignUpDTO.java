package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.User;

public class UserSignUpDTO {
    private String nickName;
    private String email;
    private String password;
    private String id;

    public UserSignUpDTO(String nickName, String email, String password,String id) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public User toUser(){
        return new User(nickName,email,password,id);
    }
}
