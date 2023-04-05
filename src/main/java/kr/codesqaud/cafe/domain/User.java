package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.dto.user.UserSaveRequest;
import kr.codesqaud.cafe.dto.user.UserUpdateRequest;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {
        // 기본 생성자가 없으면 mapping이 안된다.
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User from(UserSaveRequest userSaveRequest) { // DTO → Entity
        return new User(userSaveRequest.getUserId(), userSaveRequest.getPassword(), userSaveRequest.getName(), userSaveRequest.getEmail());
    }

    public static User from(UserUpdateRequest userUpdateRequest) { // DTO → Entity
        return new User(userUpdateRequest.getUserId(), userUpdateRequest.getNewPassword(), userUpdateRequest.getName(), userUpdateRequest.getEmail());
    }

    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getEmail() { return email; }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // 기본 생성자와 setter가 없으니 mapping이 안된다. 어떻게 동작하는 걸까?
    public String getUserId() {
        return userId;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
