package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.controller.request.UserRegisterRequest;
import kr.codesqaud.cafe.user.controller.response.UserListResponse;
import kr.codesqaud.cafe.user.controller.response.UserProfileResponse;
import kr.codesqaud.cafe.user.service.User;
import kr.codesqaud.cafe.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public String register(UserRegisterRequest userRegisterRequest, Model model) {
        boolean isSuccess = userService.register(userRegisterRequest.toUser());
        if (isSuccess) {
            return "redirect:users";
        }
        model.addAttribute("retry", true);
        model.addAttribute("error-message", "이미 사용 중인 아이디입니다.");
        return "users/form";
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

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> user = userService.login(userId, password);
        if (user.isPresent()) {
            session.setAttribute("sessionUser", user.get());
            return "redirect:/";
        } else {
            return "users/login_failed";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
