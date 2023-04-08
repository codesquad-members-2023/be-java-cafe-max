package kr.codesqaud.cafe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @GetMapping("/join")
    public String getUserForm() {
        return "user/form";             // 회원가입 페이지
    }

    @PostMapping("/users/create")
    public String joinUser(User user) {
        System.out.println(user.toString());
        return "";
    }

    @GetMapping("/users")
    public String getUserList() {
        return "list";
    }


}
