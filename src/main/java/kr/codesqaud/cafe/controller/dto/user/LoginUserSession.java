package kr.codesqaud.cafe.controller.dto.user;

import kr.codesqaud.cafe.domain.User;

public class LoginUserSession {
    private final Long id;
    private final String nickname;

    public LoginUserSession(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public LoginUserSession(User user) {
        this(user.getId(), user.getNickname());
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isOtherUser(Long id) {
        return !this.id.equals(id);
    }
}
