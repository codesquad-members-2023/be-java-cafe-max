package codesquad.cafe.domain.user.dto;

import codesquad.cafe.domain.user.domain.User;

public class UserUpdateRequestDto {
    private String password;
    private String updatedPassword;
    private String name;
    private String email;

    public UserUpdateRequestDto(String password, String updatedPassword, String name, String email) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.updatedPassword = updatedPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getUpdatedPassword() {
        return updatedPassword;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
