package codesquad.cafe.domain;

import java.time.LocalDate;

public class User {

    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDate date;

    public User(String id, String password, String name, String email, LocalDate date) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.date = date;
    }

    public String getId() {
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
