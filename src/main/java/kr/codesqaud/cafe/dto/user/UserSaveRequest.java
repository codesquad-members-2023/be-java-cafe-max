package kr.codesqaud.cafe.dto.user;

public class UserSaveRequest {
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserSaveRequest() {
    }

    public String getUserId() {
        return userId;
    }
    public String getPassword() {
        return password;
    }
    public String getName() { return name; }
    public String getEmail() {
        return email;
    }

    public void setUserId(String userId) { this.userId = userId; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}
