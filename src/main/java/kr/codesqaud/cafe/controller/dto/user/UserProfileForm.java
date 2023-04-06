package kr.codesqaud.cafe.controller.dto.user;

import kr.codesqaud.cafe.domain.User;

public class UserProfileForm {
    private Long id;
    private String name;
    private String email;

    public UserProfileForm(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
