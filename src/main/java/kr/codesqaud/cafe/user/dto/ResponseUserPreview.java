package kr.codesqaud.cafe.user.dto;

public class ResponseUserPreview {
    //회원 목록 조회용 사용자DTO
    private final String userId;

    private final String name;

    private final String email;

    public ResponseUserPreview(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
