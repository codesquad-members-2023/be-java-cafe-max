package kr.codesqaud.cafe.exception.member;

public enum MemberExceptionType {
    NOT_FOUND("error","존재하지 않는 정보입니다."),
    NOT_FOUND_USER("error", "해당 유저를 찾을 수 없습니다."),
    DO_NOT_HAVE_ACCESS("error", "접근 권한이 없습니다."),
    DUPLICATED_EMAIL("error-email", "이미 존재하는 이메일입니다."),
    DUPLICATED_MEMBER_NICKNAME("error-nickname", "이미 존재하는 닉네임입니다.");

    private final String category;
    private final String message;

    MemberExceptionType(String category, String message) {
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
