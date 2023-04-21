package kr.codesqaud.cafe.common.exception.user;

public enum UserExceptionType {
    NOT_FOUND_USER("error", "해당 유저를 찾을 수 없습니다."),
    DO_NOT_HAVE_ACCESS("error", "접근 권한이 없습니다."),
    DUPLICATED_USER_ID("error-username", "이미 존재하는 아이디입니다"),
    DUPLICATED_NICKNAME("error-nickname", "이미 존재하는 닉네임입니다."),
    NOT_MATCHED_PASSWORD("error-password", "비밀번호가 일치하지 않습니다."),
    INVALID_USER_ID("error-username", "아이디를 잘못 입력했습니다.");

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
