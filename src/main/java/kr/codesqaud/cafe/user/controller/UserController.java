package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.controller.request.UserLoginRequest;
import kr.codesqaud.cafe.user.controller.request.UserRegisterRequest;
import kr.codesqaud.cafe.user.controller.request.UserUpdateRequest;
import kr.codesqaud.cafe.user.controller.response.UserListResponse;
import kr.codesqaud.cafe.user.controller.response.UserProfileResponse;
import kr.codesqaud.cafe.user.service.User;
import kr.codesqaud.cafe.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            return "redirect:/users";
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
    public String login(UserLoginRequest userLoginRequest, HttpSession session) {
        Optional<User> user = userService.login(userLoginRequest.getUserId(), userLoginRequest.getPassword());
        if (user.isPresent()) {
            session.setAttribute("sessionUser", user.get());
            return "redirect:/";
        }
        return "users/login_failed";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/update")
    public String updateForm(Model model, HttpSession session) {
        if (session.getAttribute("sessionUser") == null) {
            return "redirect:/users/login";
        }
        model.addAttribute("retry", false);
        User user = (User) session.getAttribute("sessionUser");
        model.addAttribute("userId", user.getUserId());
        return "users/update";
    }

    @PutMapping("")
    public String updateUser(UserUpdateRequest userUpdateRequest, HttpSession session, Model model) {
        Object currentUser = session.getAttribute("sessionUser");
        if (currentUser == null) {
            return "users/login";
        }
        User user = (User) currentUser;
        boolean success = userService.updateUser(user, userUpdateRequest.toUser(), userUpdateRequest.getCurrPassword());
        if (success) {
            return "redirect:/users";
        }
        model.addAttribute("retry", true);
        model.addAttribute("error-message", "비밀번호가 잘못되었습니다. 다시 입력하세요.");
        model.addAttribute("userId", user.getUserId());
        return "users/update";
    }
}
