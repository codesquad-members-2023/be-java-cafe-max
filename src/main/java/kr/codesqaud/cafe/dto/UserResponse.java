package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.User;

public class UserResponse {
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserResponse(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static UserResponse from(User user) { // Entity → DTO
        return new UserResponse(user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public String getUserId() { return userId; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
