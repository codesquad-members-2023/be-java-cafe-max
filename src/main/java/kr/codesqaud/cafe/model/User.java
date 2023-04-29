package kr.codesqaud.cafe.model;

import kr.codesqaud.cafe.dto.SignupRequestDto;

import java.util.HashMap;
import java.util.Map;

public class User {

    public User(Long id, String email, String nickname, String password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    private Long id;
    private String email;
    private String nickname;
    private String password;


    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
        userMap.put("id", String.valueOf(this.id));
        userMap.put("email", this.email);
        userMap.put("nickname", this.nickname);
        userMap.put("password", this.password);
        return userMap;
    }
}
