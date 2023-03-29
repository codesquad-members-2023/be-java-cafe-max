package kr.codesqaud.cafe.domain;

public class User {

    private Long id;
    private String password;
    private String name;
    private String email;

    public User(Long id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
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
