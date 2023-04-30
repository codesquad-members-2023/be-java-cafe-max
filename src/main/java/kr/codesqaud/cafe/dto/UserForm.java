package kr.codesqaud.cafe.dto;

public class UserForm {

    private final String userId;
    private final String password;
    private final String userName;
    private final String email;

    public UserForm(String userId, String password, String userName, String email) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
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

    public String getEmail() {
        return email;
    }
}
