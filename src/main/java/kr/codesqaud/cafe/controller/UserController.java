package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.UserJoinDto;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String create(@ModelAttribute UserJoinDto userJoinDto) {
        final Long id = userService.join(userJoinDto);

        return "home";
    }
}
