package codesquad.cafe.global.constant;

public enum SessionAttributes {
    LOGIN_USER("loginUser");

    private String value;

    SessionAttributes(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
