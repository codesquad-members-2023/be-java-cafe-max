package kr.codesqaud.cafe.domain;

public class User {
    private static long userNumFactory = 0;    // 잠재적 문제요인(정확히 파악 아직 하는 중...)
    private long userId;
    private long userNum; // ---> setter 만드니까 빨간줄 들어와서 final 삭제
    private String userLoginId; // ---> setter 만드니까 빨간줄 들어와서 final 삭제
    private String password;  // ---> setter 만드니까 빨간줄 들어와서 final 삭제
    private String email;       // form에서 사용된 name에 맞춤   // ---> setter 만드니까 빨간줄 들어와서 final 삭제

    public User(){}
    public User(String userLoginId, String password, String email) {
        userNumFactory += 1;                  //    고민하기! -> Article에선 @Repository에서 생성(아직 여기의 static이 문제가 되는 정확한 이유는 파악하진 못한....;)
        this.userNum = userNumFactory;
        this.userLoginId = userLoginId;
        this.password = password;
        this.email = email;
    }

    public long getUserNum(){ return userNum; }
    public void setUserNum(long userNum){
        this.userNum = userNum;
    }
    public void setUserId(long Id){
        this.userId = Id;
    }
    public String getUserLoginId() {
        return userLoginId;
    }
    public void setUserLoginId(String userLoginId){
        this.userLoginId = userLoginId;
    }
    public String getPassword(){ return password; }
    public void setPassword(String password){
        this.password = password;
    }
    public String getEmail(){ return email; }
    public void setEmail(String email){
        this.email = email;
    }
}




















