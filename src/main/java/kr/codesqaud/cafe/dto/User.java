package kr.codesqaud.cafe.dto;

public class User {
    private String password;

    public User(String password){
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                '}';
    }
}
