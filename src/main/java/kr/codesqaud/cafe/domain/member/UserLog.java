package kr.codesqaud.cafe.domain.member;

import java.util.ArrayList;
import java.util.List;

public class UserLog {
    private int index;
    private String userId;
    private String password;
    private String name;
    private String email;
    public static List<UserLog> userLogList = new ArrayList<>();
    public UserLog(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.index = userLogList.size();
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
