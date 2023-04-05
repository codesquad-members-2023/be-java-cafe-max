package kr.codesqaud.cafe.web.controller;

import java.util.logging.Logger;
import javax.validation.Valid;
import kr.codesqaud.cafe.web.dto.UserResponseDto;
import kr.codesqaud.cafe.web.dto.UserSavedRequestDto;
import kr.codesqaud.cafe.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private static final Logger logger = Logger.getLogger("userController");
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/form")
    public String form() {
        return "user/form";
    }

    @PostMapping("/user/create")
    @ResponseBody
    public UserResponseDto newUser(@Valid @RequestBody UserSavedRequestDto requestDto) {
        logger.info(requestDto.toString());
        return userService.save(requestDto);
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable(value = "userId") String userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        return "user/profile";
    }
}
