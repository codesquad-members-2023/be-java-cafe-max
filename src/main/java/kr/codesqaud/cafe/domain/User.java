package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.dto.UserDTO;
import kr.codesqaud.cafe.controller.dto.UserListDTO;

public class User {

    private String nickName;
    private String email;
    private String password;
    private String id;

    public User(String nickName, String email, String password,String id) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }

    public UserDTO toUserDTO(){
        return new UserDTO(nickName,email,password,id);
    }

    public UserListDTO toUserListDTO() {
        return new UserListDTO(nickName,email,id);
    }

}
