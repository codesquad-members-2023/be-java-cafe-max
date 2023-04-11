package kr.codesqaud.cafe.dto.user;

import kr.codesqaud.cafe.domain.User;

public class UserRequestDto {
    private String id;
    private String password;
    private String name;
    private String email;

    public UserRequestDto(final String id, final String password, final String name, final String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return new User(id, password, name, email);
    }

    public String getId() {
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
