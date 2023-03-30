package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserMemoryRepository userMemoryRepository;

    @Autowired
    public UserController(UserMemoryRepository userMemoryRepository) {
        this.userMemoryRepository = userMemoryRepository;
    }

    @PostMapping("/user/signup")
    public String signUp(
            @RequestParam("userId") String userId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            Model model) {

        User user = new User();

        user.setUserId(userId);
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);

        userMemoryRepository.save(user);

        model.addAttribute("user", user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUserList(Model model){
        model.addAttribute("users", userMemoryRepository.findAll());

        return "user/list";
    }

}
