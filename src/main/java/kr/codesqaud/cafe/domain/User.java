package kr.codesqaud.cafe.domain;

public class User {
    // 사용자가 전달한 값을 저장
        // 회원가입 할 때 전달한 값을 저장할 수 있는 필드를 생성한 후 setter와 getter 메소드를 생성한다.
    // 사용자 목록을 관리하기 위해 JCF (Java Collections Framework) 를 사용한다.

    private Long number; // 회원번호
    private String userId;

    private String password;
    private String name;
    private String email;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
