package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.User;

public class LoginDTO {

    private final Long id;
    private final String userId;
    private final String password;

    public LoginDTO(Long id, String userId, String password) {
        this.id = id;
        this.userId = userId;
        this.password = password;
    }

    public static LoginDTO from(User user) {
        return new LoginDTO(user.getId(), user.getUserId(), user.getPassword());
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

}
