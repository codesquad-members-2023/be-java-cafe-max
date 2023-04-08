package kr.codesqaud.cafe.user.dto;

public class UserListDto {
    //회원 목록 조회영 사용자DTO
    private String userId;

    private String name;

    private String email;

    public UserListDto(String userId, String name, String email) {
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
