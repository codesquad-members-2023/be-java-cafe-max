package kr.codesqaud.cafe.user.domain;

public class User {

    private String id;
    private String password;
    private String name;
    private String email;
    private long index;

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

    public void setIndex(long index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
