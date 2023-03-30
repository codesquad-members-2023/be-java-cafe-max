package kr.codesqaud.cafe.domain;

public class User {
    private Long index;
    private String userID;
    private String email;
    private String nickname;
    private String password;
    private String signUpDate;


    public User(Long index, String userID, String email, String nickname, String password, String signUpDate) {
        this.index = index;
        this.userID = userID;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.signUpDate = signUpDate;
    }

    public Long getIndex() {
        return index;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getSignUpDate() {
        return signUpDate;
    }
}
