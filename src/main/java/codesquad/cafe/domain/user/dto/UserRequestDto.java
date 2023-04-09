package codesquad.cafe.domain.user.dto;

import codesquad.cafe.domain.user.domain.User;

public class UserRequestDto {
    private String id;
    private String password;
    private String name;
    private String email;

    public UserRequestDto(String id, String password, String name, String email) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
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
