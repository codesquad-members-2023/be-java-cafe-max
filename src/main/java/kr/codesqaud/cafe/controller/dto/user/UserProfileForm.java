package kr.codesqaud.cafe.controller.dto.user;

import kr.codesqaud.cafe.domain.User;

public class UserProfileForm {
    private Long id;
    private String name;
    private String email;

    public UserProfileForm() {
    }

    public UserProfileForm(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    // TODO: 아래 방법이 나은지, 위 방법이 나은지
    // 위처럼 넣어주면 DTO에 필드값이 들어가는 거고
    // 아래처럼 넣어주면 새로운 객체를 전달하는 것이니 DTO 사용 목적에 더 맞다고 보는데
    // Kyu는 어떻게 생각하시나요?
//    public UserProfileForm(Long id, String name, String email) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//    }

//    public UserProfileForm form(User user) {
//        return new UserProfileForm(user.getId(), user.getName(), user.getEmail());
//    }

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
