package kr.codesqaud.cafe.domain;

public class User {

    private final Long id;
    private final String userId;
    private final String password;
    private final String userName;
    private final String email;

    public static class Builder {

        private Long id;
        private String userId;
        private String password;
        private String userName;
        private String email;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
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

        public User build() {
            return new User(this);
        }
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.password = builder.password;
        this.userName = builder.userName;
        this.email = builder.email;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
