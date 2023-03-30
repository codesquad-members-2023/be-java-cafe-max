package kr.codesqaud.cafe.Dto;

public class UserProfileDto {

    String name;

    String email;

    public String getName() {
        return name;
    }

    public UserProfileDto(String name, String email) {
        this.name = name;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

}
