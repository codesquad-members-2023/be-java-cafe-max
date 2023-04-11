package kr.codesqaud.cafe.user.service;

public class User {

    private final Long id;
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User create(long id) {
        return new User(id, this.userId, this.password, this.name, this.email);
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

    public String getEmail() {
        return email;
    }
}
