package kr.codesqaud.cafe.controller.dto.login;

import kr.codesqaud.cafe.domain.User;

public class LoggedInDTO {
    private final Long id;
    private final String userId;
    private final String name;

    public LoggedInDTO(Long id, String userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public static LoggedInDTO from(User user) {
        return new LoggedInDTO(user.getId(), user.getUserId(), user.getName());
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
