package kr.codesqaud.cafe.controller.dto.user;

public class UserListDTO {
    private final String nickName;
    private final String email;
    private final String id;
    private final String date;
    public UserListDTO(String nickName, String email,String id,String date) {
        this.nickName = nickName;
        this.email = email;
        this.id = id;
        this.date = date;
    }
}
