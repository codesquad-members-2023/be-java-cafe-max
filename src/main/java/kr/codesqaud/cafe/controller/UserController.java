package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.SignupRequestDto;
import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        userRepository.save(signupRequestDto);
        return "회원가입 성공 DTO";
    }

    @PostMapping("/login")
    public String login(String email, String password) {
        return "토큰 DTO";
    }

    @GetMapping
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable Long userId) {
        return "특정 회원 정보 DTO";
    }

}
