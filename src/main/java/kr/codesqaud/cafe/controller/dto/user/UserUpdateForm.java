package kr.codesqaud.cafe.controller.dto.user;

import javax.validation.constraints.NotBlank;

public class UserUpdateForm {
    private Long id;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
