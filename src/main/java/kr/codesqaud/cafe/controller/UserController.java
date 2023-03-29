package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController() {
        this.userRepository = new UserRepository();
    }

    @GetMapping("/user/join")
    public String showJoinForm(){
        return "user/form";
    }

    @PostMapping("user/join")
    public String join(User user){
        userRepository.join(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUserList(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "user/list";
    }
    @GetMapping("/users/{userId}")
    public String showUserProfile(@PathVariable int userId, Model model){
        model.addAttribute("user",userRepository.getUserByUserId(userId));
        return "user/profile";
    }
}
