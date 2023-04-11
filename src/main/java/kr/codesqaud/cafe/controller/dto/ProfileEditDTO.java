package kr.codesqaud.cafe.controller.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ProfileEditDTO {

    private final String id;

    @NotBlank
    @Length(min = 2,max = 12)
    private final String nickName;

    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    private final String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private final String newPassword;

    @NotBlank
    private final String oriPassword;

    public ProfileEditDTO(String nickName, String email, String newPassword, String oriPassword,String id) {
        this.nickName = nickName;
        this.email = email;
        this.newPassword = newPassword;
        this.oriPassword = oriPassword;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getOriPassword() {
        return oriPassword;
    }

}
