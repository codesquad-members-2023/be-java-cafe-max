package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.User;

public class UserDto {
    private String nickName;
    private String email;
    private String password;

    public UserDto(String nickName, String email, String password) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toUser(int id){
        return new User(nickName,email,password,id);
    }
}
