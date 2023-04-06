package kr.codesqaud.cafe.controller.dto.user;

import kr.codesqaud.cafe.domain.User;

import javax.validation.constraints.NotBlank;

public class UserUpdateForm {
    private Long id;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    // 이 기본 생성자를 삭제한 이후 계속 인식을 못 했었습니다. ㅠㅠ
    public UserUpdateForm() {
    }

    public UserUpdateForm(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }

    // TODO: 아래 방법이 나은지, 위 방법이 나은지
    // 위처럼 넣어주면 DTO에 필드값이 들어가는 거고
    // 아래처럼 넣어주면 새로운 객체를 전달하는 것이니 DTO 사용 목적에 더 맞다고 보는데
    // Kyu는 어떻게 생각하시나요?
//    public UserUpdateForm(Long id, String name, String password, String email) {
//        this.id = id;
//        this.name = name;
//        this.password = password;
//        this.email = email;
//    }

//    public UserUpdateForm form(User user) {
//        return new UserUpdateForm(user.getId(), user.getName(), user.getPassword(), user.getEmail());
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
