package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.UserJoinDTO;
import kr.codesqaud.cafe.controller.dto.UserReadDTO;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String signUp(@ModelAttribute UserJoinDTO userJoinDTO) {
        userService.signUp(userJoinDTO);
        return "redirect:/users/list";
    }

    @GetMapping("/users/list")
    public String list(Model model) {
        List<UserReadDTO> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public String profileFrom(@PathVariable long id, Model model) {
        UserReadDTO wantedUser = userService.findOne(id);
        model.addAttribute("wantedUser", wantedUser);
        return "user/profile";
    }
}
