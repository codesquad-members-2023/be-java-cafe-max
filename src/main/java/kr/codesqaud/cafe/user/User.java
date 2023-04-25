package kr.codesqaud.cafe.user;

/**
 * 회원을 의미하는 객체
 */
public class User {

    private long id;
    private String loginId;
    private String password;
    private String name;
    private String email;

    public User(String loginId, String password, String name, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    private User(Builder builder) {
        id = builder.id;
        loginId = builder.loginId;
        password = builder.password;
        name = builder.name;
        email = builder.email;
    }

    public long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder {

        private long id;
        private String loginId;
        private String password;
        private String name;
        private String email;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder loginId(String loginId) {
            this.loginId = loginId;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
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

}
