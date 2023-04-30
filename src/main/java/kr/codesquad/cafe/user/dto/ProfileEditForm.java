package kr.codesquad.cafe.user.dto;

import kr.codesquad.cafe.user.annotation.ValidNickName;
import kr.codesquad.cafe.user.annotation.ValidPassword;
import kr.codesquad.cafe.user.domain.User;

import javax.validation.constraints.Email;

public class ProfileEditForm {
    @ValidNickName
    private final String nickname;
    @Email
    private final String email;
    @ValidPassword
    private final String password;

    public ProfileEditForm(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public static ProfileEditForm from(User user) {
        return new Builder()
                .password(user.getPassword())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
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

    public User setUser(User user) {
        return user.setProfile(nickname, email);
    }

    public static class Builder {
        private String nickname;
        private String email;
        private String password;

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public ProfileEditForm build() {
            return new ProfileEditForm(nickname, email, password);
        }
    }
}
