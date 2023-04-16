package kr.codesqaud.cafe.model;

public class User {

    public User(Long id, String email, String nickname, String password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    private Long id;
    private String email;
    private String nickname;
    private String password;


    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


}
