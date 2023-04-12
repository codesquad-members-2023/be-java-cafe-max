package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/join")
    public String getUserForm() {
        return "/user/form";             // 회원가입 페이지
    }

    @PostMapping("/users")
    public String joinUser(User user) { // 회원가입 페이지의 form으로 받은 데이터를 Post 리퀘스트 받기
        userService.join(user);
        return "redirect:/users";
    }
    @GetMapping("/users")
    public String getUserList(Model model) {
        List<User> users = userService.getUserList;
        model.addAttribute("users", users);
        return "/user/list";
    }
}
