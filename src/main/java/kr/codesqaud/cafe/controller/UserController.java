package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.UserForm;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/join")
    public String joinForm() {
        return "user/form";
    }

    @PostMapping("/user/create")
    public String create(UserForm form) {
        User user = new User(form.getUserId(), form.getPassword(), form.getName(), form.getEmail());
        user.setName(form.getName());

        userService.join(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        // TODO: @ModelAttribute <- users 저장
        model.addAttribute("users", users);
        return "user/list";
        // gradle -> 타임리프가 이걸 해줌
        // "templates/" + "user/list" + ".html"
    }
}
