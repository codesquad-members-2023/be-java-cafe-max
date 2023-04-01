package kr.codesqaud.cafe.user.domain;

import kr.codesqaud.cafe.user.dto.UserDto;

public class User {
    private String userId;
    private String password;
    private String userName;
    private String email;

    public UserDto toUserDto() {
        return new UserDto(this.userId, this.userName, this.email);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
