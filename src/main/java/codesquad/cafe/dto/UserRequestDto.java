package codesquad.cafe.dto;

import codesquad.cafe.domain.User;

import java.time.LocalDate;

public class UserRequestDto {
    private String email;
    private String name;
    private String password;
    private LocalDate date;

    public UserRequestDto(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.date = LocalDate.now();
    }

    public User toEntity() {
        return new User(email, name, password, date);
    }
}
