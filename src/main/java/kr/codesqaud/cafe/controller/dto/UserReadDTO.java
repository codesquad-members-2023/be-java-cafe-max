package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.User;

public class UserReadDTO { //회원 정보 전달용
    private final long id;
    private final String name;
    private final String userId;
    private final String email;

    public UserReadDTO(long id, String name, String userId, String email) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.email = email;
    }

    public static UserReadDTO toUserReadDTO(User user) {
        return new UserReadDTO(user.getId(), user.getName(), user.getUserId(), user.getEmail());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
