package kr.codesqaud.cafe.domain.member;

import java.util.Comparator;

public class User {

    private int index;
    private String userId;
    private String password;
    private String name;
    private String email;


    public User(int index, String userId, String password, String name, String email) {
        this.index = index;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public int getIndex() {
        return index;
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
