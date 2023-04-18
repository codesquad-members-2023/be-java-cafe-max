package kr.codesqaud.cafe.controller.user;

import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    // 회원 가입
    @GetMapping("/create")
    public String create(){
        return "user/form";
    }

    @PostMapping("/create")
    public String create(UserForm form){
        userService.join(form);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model){
        List<UserDao> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    // 유저 프로필 보기
    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model){
        UserDao userResponse = userService.findByUserId(userId);
        model.addAttribute("user", userResponse);
        return "user/profile";
    }
}
