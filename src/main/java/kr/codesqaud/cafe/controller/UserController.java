package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserMemoryRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserRepository userMemoryRepository;
    @Autowired
    public UserController(UserMemoryRepository userMemoryRepository) {
        this.userMemoryRepository = userMemoryRepository;
    }

    @GetMapping("/user/join")
    public String join() {
        return "user/form";
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

    @GetMapping("/users/{userId}")
    public String showUserProfile(
            @PathVariable("userId") String userId
            ,Model model
    ){
        model.addAttribute("userProfile", userMemoryRepository.findByUserId(userId));

        return "user/profile";
    }
}
