package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getUsersList() {
        return "user/list";
    }

    @GetMapping("/join")
    public String join() {
        return "user/form";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute User user) { // ModelAttribute 이름 미지정 시 클래스 'User'의 첫 글자를 소문자로 바꾼 'user'로 자동 설정된다.
        userRepository.save(user);
        return "redirect:/users";
    }
}
