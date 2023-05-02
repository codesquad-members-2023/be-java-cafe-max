package kr.codesqaud.cafe.model;

import kr.codesqaud.cafe.dto.SignupRequestDto;

import java.util.HashMap;
import java.util.Map;

public class User extends BaseEntity {

    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    private String email;
    private String nickname;
    private String password;


    @Override
    public Object getColumn(String column) {
        switch (column) {
            case "email":
                return this.getEmail();
            case "nickname":
                return this.getNickname();
            case "password":
                return this.getPassword();
            default:
                return null;
        }
    }


    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    // User 객체를 Map 으로 만들어서 반환
    public Map<String, String> toMap() {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", String.valueOf(this.getId()));
        userMap.put("email", this.email);
        userMap.put("nickname", this.nickname);
        userMap.put("password", this.password);
        return userMap;
    }
}
