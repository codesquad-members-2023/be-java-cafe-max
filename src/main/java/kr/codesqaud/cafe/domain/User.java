package kr.codesqaud.cafe.domain;

public class User {
    String userId;

    String password;

    String name;

    String email;

    public User(String userId, String password, String name, String email){
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
