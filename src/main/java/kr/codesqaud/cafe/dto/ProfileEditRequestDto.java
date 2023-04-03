package kr.codesqaud.cafe.dto;

import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ProfileEditRequestDto {
    private final String id;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])[a-zA-Z\\d]{8,32}$")
    private final String password;

    @NotBlank
    @Length(min = 2, max = 10)
    private final String nickName;

    public ProfileEditRequestDto(String id, String email, String password, String nickName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public static ProfileEditRequestDto of(MemberResponseDto memberResponseDto) {
        return new ProfileEditRequestDto(memberResponseDto.getId(), memberResponseDto.getEmail(), null, memberResponseDto.getNickName());
    }
}
