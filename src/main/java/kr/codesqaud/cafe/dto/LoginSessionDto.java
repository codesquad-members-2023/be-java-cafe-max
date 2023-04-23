package kr.codesqaud.cafe.dto;

public class LoginSessionDto {
    private String id;
    private String name;

    public LoginSessionDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
