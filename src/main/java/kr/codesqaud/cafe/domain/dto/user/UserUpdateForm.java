package kr.codesqaud.cafe.domain.dto.user;

import kr.codesqaud.cafe.domain.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserUpdateForm {
    private Long id;
    @Length(min = 2, max = 10, message = "이름은 2글자부터 10글자까지만 가능합니다.")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "패스워드는 영문과 특수문자 숫자를 포함하며 8글자부터 32자까지만 가능합니다.")
    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "패스워드는 영문과 특수문자 숫자를 포함하며 8글자부터 32자까지만 가능합니다.")
    @NotBlank(message = "패스워드를 입력해주세요.")
    private String existingPassword;
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    // 이 기본 생성자를 삭제한 이후 계속 db를 인식을 못 했음
    private UserUpdateForm() {
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

    public String getExistingPassword() {
        return existingPassword;
    }

    public void setExistingPassword(String existingPassword) {
        this.existingPassword = existingPassword;
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

    public void setId(Long id) {
        this.id = id;
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

    public boolean isSamePassword(String password) {
        return password.equals(existingPassword);
    }
}
