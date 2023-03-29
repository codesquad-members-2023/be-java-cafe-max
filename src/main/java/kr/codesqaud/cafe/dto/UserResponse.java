package kr.codesqaud.cafe.dto;

public class UserResponse {

    private final long id;
    private final String userId;
    private final String name;
    private final String email;

    public UserResponse(long id, String userId, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
