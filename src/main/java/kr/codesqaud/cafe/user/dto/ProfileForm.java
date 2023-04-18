package kr.codesqaud.cafe.user.dto;

import kr.codesqaud.cafe.user.User;

public class ProfileForm {
    private final String nickname;
    private final String email;

    public ProfileForm(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    private ProfileForm(Builder builder) {
        this.nickname = builder.nickname;
        this.email = builder.email;
    }

    public static ProfileForm from(User user) {
        return new Builder()
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

    public static class Builder {
        private String nickname;
        private String email;

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public ProfileForm build() {
            return new ProfileForm(this);
        }
    }
}
