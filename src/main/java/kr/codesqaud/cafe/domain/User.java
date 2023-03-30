package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.dto.UserDto;

public class User {

    private String nickName;
    private String email;
    private String password;
    private int Id;

    public User(String nickName, String email, String password, int userId) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.Id = userId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
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
    public UserDto toDTO(){
        return new UserDto(nickName,email,password);
    }
}
