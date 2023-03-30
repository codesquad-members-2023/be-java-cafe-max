package kr.codesqaud.cafe.dto;

public class UserDTO {
    private int idx;
    private String userId;
    private String name;
    private String email;

    public UserDTO(int idx, String userId, String name, String email) {
        this.idx = idx;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
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

    @Override
    public String toString() {
        return "User{" +
                "idx=" + idx +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
