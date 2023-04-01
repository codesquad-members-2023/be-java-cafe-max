package kr.codesqaud.cafe.user;

public class User {
    private Long sequence;
    private String userId;
    private String password;
    private String userName;
    private String userEmail;


    public User(String userId, String password, String userName, String userEmail) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(final Long sequence) {
        this.sequence = sequence;
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
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
