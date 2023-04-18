package kr.codesqaud.cafe.dto;

public class UserDto {

    private final String userId;
    private final String userName;
    private final String email;

    public static class Builder {

        private String userId;
        private String userName;
        private String email;

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }

    private UserDto(Builder builder) {
        this.userId = builder.userId;
        this.userName = builder.userName;
        this.email = builder.email;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
