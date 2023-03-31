package kr.codesqaud.cafe.controller.dto;

public class UserJoinDTO { //회원가입 시 정보 전달용

    private final String userId;
    private final String name;
    private final String password;
    private final String email;

    public UserJoinDTO(String userId, String name, String password, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
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
