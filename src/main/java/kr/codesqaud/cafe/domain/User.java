package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.dto.request.JoinRequest;

public class User {
    private Long id;
    private final String userId;
    private String password;
    private String userName;
    private String userEmail;


    public User(String userId, String password, String userName, String userEmail) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public static User from(final JoinRequest joinRequest) {
        return new User(joinRequest.getUserId(),
                joinRequest.getPassword(),
                joinRequest.getUserName(),
                joinRequest.getUserEmail());
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isSamePassword(final String password) {
        return this.password.equals(password);
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
