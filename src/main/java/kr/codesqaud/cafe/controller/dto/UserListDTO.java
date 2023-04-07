package kr.codesqaud.cafe.controller.dto;

public class UserListDTO {
    private String nickName;
    private String email;
    private String id;
    private String date;
    public UserListDTO(String nickName, String email,String id,String date) {
        this.nickName = nickName;
        this.email = email;
        this.id = id;
        this.date = date;
    }
}
