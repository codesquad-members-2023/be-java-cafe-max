package kr.codesqaud.cafe.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UpdateFormDto {
    private String userId;
    @NotBlank(message = "공백은 입력할 수 없습니다.")
    private String password;
    @NotBlank(message = "공백은 입력할 수 없습니다.")
    private String newPassword;
    @NotBlank(message = "공백은 입력할 수 없습니다.")
    @Length(message = "2글자~10글자 사이로 입력", min = 2, max = 10)
    private String name;
    @Email
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
