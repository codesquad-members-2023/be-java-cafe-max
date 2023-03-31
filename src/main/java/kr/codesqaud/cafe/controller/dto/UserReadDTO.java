package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.User;

public class UserReadDTO { //회원 정보 전달용
    private final long id;
    private final String userId;
    private final String name;
    private final String email;

    public UserReadDTO(long id, String userId, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public static UserReadDTO toUserReadDTO(User user) {
        return new UserReadDTO(user.getId(), user.getUserId(), user.getName(), user.getEmail());
    }

    public long getId() {
        return id;
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
