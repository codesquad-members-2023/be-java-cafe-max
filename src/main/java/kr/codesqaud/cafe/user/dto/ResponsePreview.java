package kr.codesqaud.cafe.user.dto;

public class ResponsePreview {
    //회원 목록 조회영 사용자DTO
    private final String userId;

    private final String name;

    private final String email;

    public ResponsePreview(String userId, String name, String email) {
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
