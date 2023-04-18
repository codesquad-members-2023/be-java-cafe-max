package kr.codesqaud.cafe.user.dto;

import kr.codesqaud.cafe.user.User;

import java.util.Objects;

public class UserForm {
    private final Long id;
    private final String nickname;
    private final String email;

    public UserForm(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserForm)) return false;
        UserForm userForm = (UserForm) o;
        return Objects.equals(getId(), userForm.getId()) && Objects.equals(getNickname(), userForm.getNickname()) && Objects.equals(getEmail(), userForm.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNickname(), getEmail());
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
