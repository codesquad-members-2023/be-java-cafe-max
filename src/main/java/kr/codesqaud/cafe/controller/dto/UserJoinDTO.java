package kr.codesqaud.cafe.controller.dto;

public class UserJoinDTO { //회원가입 시 정보 전달용

    private final Long id;
    private final String name;
    private final String userId;
    private final String password;
    private final String email;

    public UserJoinDTO(Long id, String name, String userId, String password, String email) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
