package codesquad.cafe.user.dto;

import codesquad.cafe.user.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserRequestDto {
    @Pattern(regexp = "^[0-9a-z]+$", message = "아이디는 숫자, 영문만 입력 가능합니다.")
    private String id;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\w\\W]{8,}$", message = "비밀번호는 최소 8자, 문자와 숫자는 하나 이상 포함되어야 합니다.")
    private String password;
    @NotBlank(message = "이름은 반드시 입력해야 합니다.")
    private String name;
    @NotBlank
    @Email(message = "유효하지 않은 이메일 주소입니다.")
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
