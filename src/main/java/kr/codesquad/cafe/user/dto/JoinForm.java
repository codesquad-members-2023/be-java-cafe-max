package kr.codesquad.cafe.user.dto;

import kr.codesquad.cafe.user.annotation.PasswordMatches;
import kr.codesquad.cafe.user.annotation.ValidEmail;
import kr.codesquad.cafe.user.annotation.ValidNickName;
import kr.codesquad.cafe.user.annotation.ValidPassword;
import kr.codesquad.cafe.user.domain.User;
import org.jasypt.encryption.StringEncryptor;

public class JoinForm {
    @ValidNickName
    private final String nickname;
    @ValidEmail
    private final String email;
    @ValidPassword
    private final String password;
    @ValidPassword
    private final String reconfirmPassword;

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

    @PasswordMatches
    public boolean isSamePassword() {
        return password.equals(reconfirmPassword);
    }
}
