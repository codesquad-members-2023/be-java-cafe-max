package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserMemoryRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserMemoryRepository userMemoryRepository) {
        this.userRepository = userMemoryRepository;
    }

    @RequestMapping("/user/signup")
    public String join(HttpServletRequest request, @ModelAttribute User user){

        if("POST".equals(request.getMethod())){
            userRepository.save(user);
            return "redirect:/users";
        }

        return "user/form";
    }

    @GetMapping("/users")
    public String showUserList(Model model){
        model.addAttribute("users", userRepository.findAll());

        return "user/list";
    }

    @GetMapping("/user/{userId}")
    public String showUserProfile(
            @PathVariable("userId") String userId
            ,Model model
    ){
        model.addAttribute("userProfile", userRepository.findByUserId(userId));

        return "user/profile";
    }

    @GetMapping("/user/{userId}/update")
    public String showPasswordEditForm(@PathVariable("userId") String userId, Model model){
        model.addAttribute("user", userRepository.findByUserId(userId));

        return "user/form_update";
    }

    @PutMapping("/user/{userId}/update")
    public String updatePassowrd(
            @PathVariable("userId") String userId,
            @RequestParam("newPassword") String password,
            Model model
    ){
        User user = userRepository.findByUserId(userId);

        userRepository.updateUserPassword(user, password);

        model.addAttribute("user", user);

        return "redirect:/users";
    }
}
