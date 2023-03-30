package kr.codesqaud.cafe.Dto;

public class UserListDto {
    String userId;

    String name;

    String email;

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
