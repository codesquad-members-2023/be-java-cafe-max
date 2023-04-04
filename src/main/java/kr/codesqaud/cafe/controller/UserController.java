package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.user.UserResponse;
import kr.codesqaud.cafe.dto.user.UserSaveRequest;
import kr.codesqaud.cafe.dto.user.UserUpdateRequest;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("userSaveRequest", new UserSaveRequest());
        return "user/sign-up";
    }

    @PostMapping
    public String signUp(@ModelAttribute UserSaveRequest userSaveRequest) { // ModelAttribute 이름 미지정 시 클래스 'UserSaveRequest'의 첫 글자를 소문자로 바꾼 'userSaveRequest'로 자동 설정된다.
        userService.saveUser(userSaveRequest);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String showUserProfile(@PathVariable String userId, Model model) {
        UserResponse userResponse = userService.findByUserId(userId);
        model.addAttribute("user", userResponse);

        return "user/profile";
    }

    @GetMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId, Model model) {
        UserResponse userResponse = userService.findByUserId(userId);
        model.addAttribute("user", userResponse);

        return "user/update";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(@ModelAttribute UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
        return "redirect:/users";
    }
}
