package kr.codesquad.cafe.user.dto;

import kr.codesquad.cafe.user.domain.User;

public class UserForm {
    private final Long id;
    private final String nickname;
    private final String email;

    private UserForm(Builder builder) {
        this.id = builder.id;
        this.nickname = builder.nickname;
        this.email = builder.email;
    }

    public static UserForm from(User user) {
        return new Builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }

    public Long getId() {
        return id;
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
        private Long id;

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public UserForm build() {
            return new UserForm(this);
        }
    }
}
