package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/join")
    public String join() {
        return "user/join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute User user) { // ModelAttribute 이름 미지정 시 클래스 'User'의 첫 글자를 소문자로 바꾼 'user'로 자동 설정된다.
        if (userRepository.isExists(user.getUserId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다");
        }
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable("userId") String userId, Model model) {
        User user = userRepository.findByUserId(userId);
        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("/{userId}/update")
    public String updateUser(@PathVariable("userId") String userId, Model model) {
        User user = userRepository.findByUserId(userId);
        model.addAttribute("user", user);

        return "user/update";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(@ModelAttribute User user, String match) {
        User findUser = userRepository.findByUserId(user.getUserId());

        if (!match.equals(findUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        userRepository.save(user);
        return "redirect:/users";
    }
}
