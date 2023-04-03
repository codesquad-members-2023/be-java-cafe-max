package kr.codesqaud.cafe.domain;

public class User {
    private String id;
    private String password;
    private String name;
    private String email;

    public User(String id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }
    public String getId() {
        return id;
    }
//
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }


}
