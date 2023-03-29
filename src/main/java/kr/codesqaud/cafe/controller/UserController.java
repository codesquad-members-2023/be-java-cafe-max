package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired //todo : 의존성 주입 시 생략 가능?
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/join")
    public String joinForm() {
        return "user/form";
    }

    @PostMapping("/user/join")
    public String signUp(@ModelAttribute User user) { // todo : 회원가입용 dto 만드는게 좋음(검증 필요)
        userService.signUp(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }
}
