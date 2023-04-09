package kr.codesqaud.cafe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserService userService = new UserService();

    @GetMapping("/join")
    public String getUserForm() {
        return "user/form";             // 회원가입 페이지
    }

    @PostMapping("/users")
    public String joinUser(User user) { // 회원가입 페이지의 form으로 받은 데이터를 Post 리퀘스트 받기



        return "";
    }
    @GetMapping("/users")
    public String getUserList() {
        return "list";
    }
}
