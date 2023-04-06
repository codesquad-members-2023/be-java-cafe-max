package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserForm;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user/create")
    public String create(UserForm form) {
        User user = new User(form.getUserId(), form.getPassword(), form.getName(), form.getEmail());

        boolean result = userService.join(user);

        if (result) {            // 회원가입 성공시
            return "redirect:/users";
        }
        return "redirect:/user/form";    // 회원가입 실패시

    }

    @GetMapping("users")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("users/{userId}")
    public String profile(Model model, @PathVariable String userId) {
        model.addAttribute("user", userService.findOne(userId));
        return "user/profile";
    }

}
