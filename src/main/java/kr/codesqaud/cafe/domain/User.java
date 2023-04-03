package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.dto.user.UserSaveRequest;
import kr.codesqaud.cafe.dto.user.UserUpdateRequest;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

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

    public String getUserId() {
        return userId;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getEmail() { return email; }
}
