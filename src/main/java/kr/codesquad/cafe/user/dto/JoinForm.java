package kr.codesquad.cafe.user.dto;

import kr.codesquad.cafe.user.annotation.ValidEmail;
import kr.codesquad.cafe.user.annotation.ValidNickName;
import kr.codesquad.cafe.user.annotation.ValidPassword;
import kr.codesquad.cafe.user.annotation.ValidTwoPassword;
import kr.codesquad.cafe.user.domain.User;
import org.jasypt.encryption.StringEncryptor;

@ValidTwoPassword
public class JoinForm {
    @ValidNickName
    private String nickname;
    @ValidEmail
    private String email;
    @ValidPassword
    private String password;

    @ValidPassword
    private String reconfirmPassword;

    public JoinForm(String nickname, String email, String password, String reconfirmPassword) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.reconfirmPassword = reconfirmPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getReconfirmPassword() {
        return reconfirmPassword;
    }

    public User toUser(StringEncryptor encryptor) {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(encryptor.encrypt(password))
                .build();
    }

    public boolean isSamePassword() {
        return password.equals(reconfirmPassword);
    }
}
