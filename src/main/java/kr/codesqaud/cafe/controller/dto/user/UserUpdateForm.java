package kr.codesqaud.cafe.controller.dto.user;

import kr.codesqaud.cafe.domain.User;

import javax.validation.constraints.NotBlank;

public class UserUpdateForm {
    private Long id;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    // 이 기본 생성자를 삭제한 이후 계속 db를 인식을 못 했음
    public UserUpdateForm() {
    }

    private UserUpdateForm(Long id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public static UserUpdateForm form(User user) {
        return new UserUpdateForm(user.getId(), user.getName(), user.getPassword(), user.getEmail());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
