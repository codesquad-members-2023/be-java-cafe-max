package kr.codesqaud.cafe.controller.dto;

public class SignInDTO {

    private final String id;

    private final String password;

    public SignInDTO(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
