package kr.codesqaud.cafe.user.dto;

public class UserProfileDto {
    //회원 프로필 내용 DTO

    private String name;

    private String email;

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
