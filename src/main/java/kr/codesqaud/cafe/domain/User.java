package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.dto.ModifiedUserDTO;

public class User {
    private Long id;
    private String userId;
    private String name;
    private String password;
    private String email;

    public User() {}

    public User(String userId, String name, String password, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public boolean matchPassword(String originPassword) {
        if(this.password == null) {
            return false;
        }
        return this.password.equals(originPassword);
    }

    public void create(Long id, User user) {
        this.id = id;
        this.userId = user.getUserId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }

    public void update(ModifiedUserDTO modifiedUserDTO) {
        this.name = modifiedUserDTO.getNewName();
        this.password = modifiedUserDTO.getNewPassword();
        this.email = modifiedUserDTO.getNewEmail();
    }

//    public Long getId() {
//        return id;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public String getEmail() {
//        return email;
//    }

        public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
