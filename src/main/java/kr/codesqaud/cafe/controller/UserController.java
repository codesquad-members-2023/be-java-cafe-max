package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.user.MemoryUserRepository;
import kr.codesqaud.cafe.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final MemoryUserRepository memoryUserRepository;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(MemoryUserRepository memoryUserRepository) {
        this.memoryUserRepository = memoryUserRepository;
    }

    @GetMapping("/user/form")
    public String form() {
        return "user/form";
    }

    @PostMapping("/user/form")
    public String post(
            @RequestParam("userId") String userId,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String email) {

        User user = new User(userId, password, name, email);
        memoryUserRepository.save(user);
        logger.info(user.toString());

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String joinSuccess(Model model) {
        // user 전체 정보 가져오기
        // 모델에 태워 보내기
        // 보낸 정보 타임리프로 출력하기
        model.addAttribute("users", memoryUserRepository.findAll());
        return "user/list";
    }
}