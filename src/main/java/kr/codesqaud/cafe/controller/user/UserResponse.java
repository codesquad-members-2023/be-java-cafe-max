package kr.codesqaud.cafe.controller.user;

public class UserResponse {
    private Long customerId; // 회원 번호
    private String userId;
    private String name;
    private String email;

    public UserResponse() {
    }

    public UserResponse(Long customerId, String userId, String name, String email) {
        this.customerId = customerId;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public Long getCustomerId(){
        return customerId;
    }

    public String getUserId() {
        return userId;
    }


    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

}
