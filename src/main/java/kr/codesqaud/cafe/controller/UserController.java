package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequestMapping
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user/form")
    public String loginPage() {
        return "user/form";
    }
    // 정보를 받아오는 느낌

    @PostMapping("user/form")
    public String register(User user) {
        userService.join(user);
        return "redirect:/user/list";
    }
    @GetMapping("user/list")
    public String getUserList(Model model) {
        List<User> userList = userService.showAllUser();
        model.addAttribute("userList", userList);
        return "user/list";
    }

    // ID, PW를 보내면
    // 글쓰기 같은 것?


}
