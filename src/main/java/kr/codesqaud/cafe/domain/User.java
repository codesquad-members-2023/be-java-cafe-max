package kr.codesqaud.cafe.domain;

public class User {
    private long id;           // 자꾸 헷갈려서 단순하게 변경   // 아이디
    private String userId;     // form에서 사용된 name에 맞춤  // 닉네임
    private String password;
    private String email;

    public User(){}  // 없으면 (Row?)Mapping이 안된다고 하는데....!!!??
    public User(String userId, String password, String email) {
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public long getId() { return id; }
    public void setId(long id){ this.id = id; }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password = password;
    }
    public String getEmail(){ return email; }
    public void setEmail(String email){
        this.email = email;
    }
}




















