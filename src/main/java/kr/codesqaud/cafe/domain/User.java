package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.dto.request.JoinRequest;

public class User {
    private final Long id;
    private final String userId;
    private String password;
    private String userName;
    private String userEmail;

    public User(Long id, String userId, String password, String userName, String userEmail) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    private User(String userId, String password, String userName, String userEmail) {
        this(null, userId, password, userName, userEmail);
    }

    public static User from(final JoinRequest joinRequest) {
        return new User(
                joinRequest.getUserId(),
                joinRequest.getPassword(),
                joinRequest.getUserName(),
                joinRequest.getUserEmail());
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void editProfile(final String password, final String userName, final String userEmail) {
        this.password = password;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
