package kr.codesqaud.cafe.Dto;

public class UserProfileDto {
    //회원 프로필 내용 DTO

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
