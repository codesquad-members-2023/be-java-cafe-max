package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.RoginRequestDto;
import kr.codesqaud.cafe.dto.SignupRequestDto;
import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserRepository userRepository) {
        this.userService = new UserService(userRepository);
    }

    @PostMapping
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signUp(signupRequestDto);
        return "회원가입 성공 DTO";
    }

    @PostMapping("/login")
    public String login(@RequestBody RoginRequestDto roginRequestDto) {
        return userService.login(roginRequestDto);
    }

    @GetMapping
    public List<User> list() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public User profile(@PathVariable Long userId) {
        return userService.findById(userId);
    }

}
