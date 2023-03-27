package codesquad.cafe.domain;

import java.time.LocalDate;

public class User {

    private Long id;
    private String email;
    private String name;
    private String password;
    private LocalDate date;

    public User(String email, String name, String password, LocalDate date) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDate() {
        return date;
    }
}
