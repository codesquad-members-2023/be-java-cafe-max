package kr.codesqaud.cafe.domain;

public class User {
    private static long userNumFactory = 0;
    private final long userNum;
    private final String userId;
    private final String password;
    private final String email;       // form에서 사용된 name에 맞춤

    public User(String userId, String password, String email) {
        userNumFactory += 1;                  //    고민하기! -> Article에선 @Repository에서 생성(아직 여기의 static이 문제가 되는 정확한 이유는 파악하진 못한....;)
        this.userNum = userNumFactory;
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }
}




















