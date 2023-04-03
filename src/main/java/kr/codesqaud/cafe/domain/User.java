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

//    public String getPassword() {
//        return password;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
    public String getId() {
        return id;
    }
//
    public String getEmail() {
        return email;
    }
//
    public String getName() {
        return name;
    }


}
