package kr.codesqaud.cafe.domain;

public class User {

    private String nickName;
    private String email;
    private String password;
    private String id;

    public User(String nickName, String email, String password,String id) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }

}
