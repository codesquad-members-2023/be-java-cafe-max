package kr.codesqaud.cafe.domain.dto.user;

import kr.codesqaud.cafe.domain.User;

public class UserProfileForm {
    private Long id;
    private String name;
    private String email;

    public UserProfileForm() {
    }

    private UserProfileForm(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static UserProfileForm form(User user) {
        return new UserProfileForm(user.getId(), user.getName(), user.getEmail());
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
