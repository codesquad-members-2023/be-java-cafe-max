package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.SignupRequestDto;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public String signup(SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "회원가입 성공 DTO";
    }

    @PostMapping("/login")
    public String login(String email, String password) {
        return "토큰 DTO";
    }

    @GetMapping
    public String list() {
        return "회원 목록 DTO";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable Long userId) {
        return "특정 회원 정보 DTO";
    }

}
