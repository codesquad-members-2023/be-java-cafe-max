package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.dto.UserJoinDTO;

public class User {
    private long id;
    private String userId;
    private String name;
    private String password;
    private String email;

    public static User toUser(UserJoinDTO userJoinDTO) {
        User user = new User();
        user.setUserId(userJoinDTO.getUserId());
        user.setName(userJoinDTO.getName());
        user.setPassword(userJoinDTO.getPassword());
        user.setEmail(userJoinDTO.getEmail());
        return user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
