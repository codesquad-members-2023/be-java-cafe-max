package kr.codesqaud.cafe.domain;

public class User {

    private String nickName;
    private String email;
    private String password;
    private int id = -1;

    public User(String nickName, String email, String password) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
    }

    public User(String nickName, String email, String password, int userId) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.id = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

}
