package kr.codesqaud.cafe.domain;

public class User {
    private long id;
    private String userId;
    private String password;
    private String email;

    public User(){}
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




















