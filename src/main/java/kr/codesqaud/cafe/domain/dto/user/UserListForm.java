package kr.codesqaud.cafe.domain.dto.user;

import kr.codesqaud.cafe.domain.User;

public class UserListForm {
    private Long id;
    private String userId;
    private String name;
    private String email;

    private UserListForm() {
    }

    private UserListForm(Long id, String userId, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public static UserListForm from(User user) {
        return new UserListForm(user.getId(), user.getUserId(), user.getName(), user.getEmail());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
