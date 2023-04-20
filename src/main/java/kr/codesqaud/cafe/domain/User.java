package kr.codesqaud.cafe.domain;

public class User {
    private static long userNumFactory = 0;
    private final long userNum;
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

    public String getUserId() {
        return userId;
    }
}




















