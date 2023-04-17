package codesquad.cafe.domain.user.domain;

import codesquad.cafe.domain.user.dto.UserRequestDto;
import codesquad.cafe.domain.user.dto.UserUpdateRequestDto;

import java.time.LocalDate;

public class User {

    private String id;
    private String password;
    private String name;
    private String email;

    public User(String id, String password, String name, String email) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
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

    public User update(final UserUpdateRequestDto userUpdateRequestDto) {
        this.name = userUpdateRequestDto.getName();
        this.email = userUpdateRequestDto.getEmail();
        return this;
    }
}
