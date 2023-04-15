package kr.codesqaud.cafe.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProfileEditRequestDto {
    private final Long memberId;

    @NotBlank(message = "이메일을 입력하세요")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private final String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8, max = 12, message = "비밀번호는 {min} ~ {max} 길이로 입력하세요.")
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+|~=`{}\\[\\]:\";'\\\\<>,.?/\\-]+", message = "비밀번호는 알파벳, 숫자, 특수문자만 가능합니다.")
    private final String password;

    @NotBlank(message = "닉네임을 입력하세요")
    @Size(min = 2, max = 10, message = "유효한 닉네임을 입력하세요")
    @Pattern(regexp = "^[가-힣]{2,4}$", message = "닉네임 형식에 맞게 입력하세요.")
    private final String nickName;

    public ProfileEditRequestDto(Long memberId, String email, String password, String nickName) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    public Long getMemberId() {
        return memberId;
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
        return new ProfileEditRequestDto(memberResponseDto.getMemberId(), memberResponseDto.getEmail(), null, memberResponseDto.getNickName());
    }
}
