package kr.codesqaud.cafe.dto;

public class UserDto {
    private String userID;
    private String email;
    private String nickname;
    private String password;

    public String getUserID() {
        return userID;
    }

    public boolean setUserID(String userID) {
        this.userID = userID;
        return true;
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        this.email = email;
        return true;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean setNickname(String nickname) {
        this.nickname = nickname;
        return true;
    }

    public String getPassword() {
        return password;
    }

    public boolean setPassword(String password) {
        this.password = password;
        return true;
    }
}
