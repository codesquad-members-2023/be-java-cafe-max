package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.User;

public class ProfileDTO { //프로필용
    private final Long id;
    private final String userId;
    private final String name;
    private final String email;

//    profileDTO는 직접 입력값을 받아서 생성되지 않으므로 private 선언
    private ProfileDTO(Long id, String userId, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    //Entity -> DTO
//    public static ProfileDTO from(final User user) {
//        return new ProfileDTO(user.getId(), user.getUserId(), user.getName(), user.getEmail());
//    }

    public Long getId() {
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
