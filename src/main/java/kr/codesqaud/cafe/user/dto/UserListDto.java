package kr.codesqaud.cafe.user.dto;

public class UserListDto {
    //회원 목록 조회영 사용자DTO
    private String userId;

    private String name;

    private String email;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
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
