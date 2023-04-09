package kr.codesqaud.cafe.domain;

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

    public boolean isPasswordMatched(String password) { // 비밀번호 일치 여부 반환
        return this.password.equals(password);
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

    // 기본 생성자와 setter가 없으니 mapping이 안된다.
    public void setUserId(String userId) {
        this.userId = userId;
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
