package codesquad.cafe.user.dto;

public class UserLoginRequestDto {
    private String userId;
    private String password;

    public UserLoginRequestDto(final String userId, final String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
