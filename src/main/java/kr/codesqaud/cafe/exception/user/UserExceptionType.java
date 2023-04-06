package kr.codesqaud.cafe.exception.user;

public enum UserExceptionType {
    DUPLICATED_USER_ID("error-userId", "이미 존재하는 아이디입니다"),
    DUPLICATED_EMAIL("error-email", "이미 존재하는 이메일입니다."),
    NOT_MATCHED_BEFORE_PASSWORD("error-password", "비밀번호가 일치하지 않습니다.");

    private final String category;
    private final String message;

    UserExceptionType(String category, String message) {
        this.category = category;
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public String getMessage() {
        return message;
    }
}
