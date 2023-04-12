package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.dto.user.UserResponseDto;

public class User {
    private String id;
    private String password;
    private String name;
    private String email;

    public User(final String id, final String password, final String name, final String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    //
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public UserResponseDto toDto() {
        return new UserResponseDto(id, name, email);
    }


}
