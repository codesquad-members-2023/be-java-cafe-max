package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.request.JoinRequest;
import kr.codesqaud.cafe.controller.dto.request.ProfileEditRequest;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create-user-form")
    public String showJoinForm() {
        return "user/form";
    }

    @GetMapping("/login-user-form")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute JoinRequest joinRequest) {
        userService.join(joinRequest);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String listAllUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showProfilePage(@PathVariable final String userId, final Model model) {
        model.addAttribute("user", userService.findByUserId(userId));
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String showProfileEditPage(@PathVariable final String userId, final Model model) {
        model.addAttribute("userId", userId);
        return "user/edit_form";
    }

    @PutMapping("/users/{userId}")
    public String editUserProfile(@PathVariable final String userId, @ModelAttribute final ProfileEditRequest request) {
        userService.editUserProfile(userId, request);
        return "redirect:/users";
    }
}
