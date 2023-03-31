package kr.codesqaud.cafe.controller.dto;

public class ProfileEditDto {
    private String nickName;
    private String email;
    private String newPassword;
    private String oriPassword;
    private Integer id;

    public ProfileEditDto(String nickName, String email, String newPassword, String oriPassword, Integer id) {
        this.nickName = nickName;
        this.email = email;
        this.newPassword = newPassword;
        this.oriPassword = oriPassword;
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOriPassword() {
        return oriPassword;
    }

    public void setOriPassword(String oriPassword) {
        this.oriPassword = oriPassword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto toUserDto(){
        return new UserDto(nickName,email,newPassword,id);
    }
}
