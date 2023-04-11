package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.User;

public class JoinDTO { //회원가입용

    private final Long id;
    private final String userId;
    private final String name;
    private final String password;
    private final String email;

    //회원정보 수정 시 입력값을 받아서 객체 생성되므로 public 선언
    public JoinDTO(Long id, String userId, String name, String password, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    //DTO -> Entity
    public User toEntity() {
        return new User(userId, name, password, email);
    }

    public static JoinDTO from(User user) {
        return new JoinDTO(user.getId(), user.getUserId(), user.getName(), user.getPassword(), user.getEmail());
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

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
