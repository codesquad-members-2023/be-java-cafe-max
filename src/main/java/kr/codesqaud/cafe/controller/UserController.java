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

    @PostMapping("/user/form")
    public String saveUser(User user) {

        memoryUserRepository.save(user);
        logger.info(user.toString());

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {

        model.addAttribute("users", memoryUserRepository.findAll());

        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(@PathVariable("userId") String userId, Model model) {

        User user = memoryUserRepository.findById(userId);
        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("/users/{userId}/check")
    public String showCheckPasswordForm(@PathVariable String userId, Model model) {

        User user = memoryUserRepository.findById(userId);
        model.addAttribute("user", user);

        return "user/checkPassword";
    }

    @PutMapping("/users/{userId}/check")
    public String checkPassword(@PathVariable String userId, String password, Model model) {

        User user = memoryUserRepository.findById(userId);
        model.addAttribute("user", user);

        if (!user.getPassword().equals(password)) {
            model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");

            return "redirect:/users";
        }

        model.addAttribute("successMessage", "비밀번호가 일치합니다.");

        return "redirect:/users/{userId}/form";
    }

    @GetMapping("/users/{userId}/form")
    public String showUpdateForm(@PathVariable String userId, Model model) {

        User user = memoryUserRepository.findById(userId);
        model.addAttribute("user", user);

        return "user/updateForm";
    }

    @PutMapping("/users/{userId}/update")
    public String update(@ModelAttribute User user) {

        memoryUserRepository.update(user);
        logger.info(user.toString());

        return "redirect:/users";
    }
}
