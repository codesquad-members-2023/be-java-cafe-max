package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.controller.request.UserRegisterRequest;
import kr.codesqaud.cafe.user.controller.response.UserListResponse;
import kr.codesqaud.cafe.user.controller.response.UserProfileResponse;
import kr.codesqaud.cafe.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public String register(UserRegisterRequest userRegisterRequest) {
        userService.register(userRegisterRequest.toUser());
        return "redirect:users";
    }

    @GetMapping("")
    public String showUserList(Model model) {
        List<UserListResponse> users = userService.getUserList();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/profile/{id}")
    public String showUser(@PathVariable("id") String id, Model model) {
        UserProfileResponse profile = userService.getProfile(id);
        model.addAttribute("name", profile.getName());
        model.addAttribute("email", profile.getEmail());
        return "users/profile";
    }
}
