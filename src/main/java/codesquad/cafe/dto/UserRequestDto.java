package codesquad.cafe.dto;

import codesquad.cafe.domain.User;

import java.time.LocalDate;

public class UserRequestDto {
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDate date;

    public UserRequestDto(String id, String password, String name, String email) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.date = LocalDate.now();
    }

    public User toEntity() {
        return new User(id, password, name, email, date);
    }
}
