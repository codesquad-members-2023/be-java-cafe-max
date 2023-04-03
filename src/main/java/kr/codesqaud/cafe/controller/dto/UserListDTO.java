package kr.codesqaud.cafe.controller.dto;

public class UserListDTO {
    private String nickName;
    private String email;
    private String id;

    public UserListDTO(String nickName, String email,String id) {
        this.nickName = nickName;
        this.email = email;
        this.id = id;
    }
}
