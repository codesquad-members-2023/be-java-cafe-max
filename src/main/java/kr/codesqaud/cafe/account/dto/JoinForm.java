package kr.codesqaud.cafe.account.dto;

import kr.codesqaud.cafe.account.User;
import kr.codesqaud.cafe.account.annotation.ValidEmail;
import kr.codesqaud.cafe.account.annotation.ValidNickName;
import kr.codesqaud.cafe.account.annotation.ValidPassword;
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
        return new User.Builder()
                .nickname(nickname)
                .email(email)
                .password(encryptor.encrypt(password))
                .build();
    }

    public boolean isSamePassword() {
        return password.equals(reconfirmPassword);
    }
}
