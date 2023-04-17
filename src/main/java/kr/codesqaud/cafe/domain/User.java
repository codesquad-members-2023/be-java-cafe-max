package kr.codesqaud.cafe.domain;

public class User {
    private static int userNumFactory = 0;
    private final int userNum;
    private String userId;
    private String password;
    private String email;       // form에서 사용된 name에 맞춤

    public User(String userId, String password, String email) {
        userNumFactory += 1;                  //    고민하기!
        this.userNum = userNumFactory;
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public static int getUserNumFactory() {
        return userNumFactory;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}




















