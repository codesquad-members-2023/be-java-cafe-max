package kr.codesqaud.cafe.domain;

public class User {

    private String id;
    private String password;
    private String name;
    private String email;

    public static class Builder {

        private String id;
        private String password;
        private String name;
        private String email;

        public Builder() {
        }

        public Builder id(String id) {
            this.id = id;
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

    public User(Builder builder) {
        id = builder.id;
        password = builder.password;
        name = builder.name;
        email = builder.email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
