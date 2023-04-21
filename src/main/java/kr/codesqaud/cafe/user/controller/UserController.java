package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.service.LoginService;
import kr.codesqaud.cafe.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    @Autowired
    public UserController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @GetMapping("/user/form")
    public String form() {
        return "user/form";
    }

    @GetMapping("/user/list")
    public String list(Model model) {
        List<kr.codesqaud.cafe.user.domain.User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/user/login")
    public String loginSubmit(LoginForm loginForm, HttpSession httpSession) {
        try{
            User requestUser = userService.findById(loginForm.getUserId());
            loginService.validationRequestPassword(requestUser.getPassword(),loginForm.getPassword());
            httpSession.setAttribute("sessionUser", requestUser);
        }
        catch(AccessDeniedException | NullPointerException e){
            System.out.println(e.getMessage());
            return "user/login_failed";
        }
        return "redirect:/";
    }

    @GetMapping("/user/login_failed")
    public String loginFailed() {
        return "user/login_failed";
    }

    @GetMapping("/user/{id}")
    public String profile(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/profile";
    }

    @PostMapping("/user/form")
    public String create(UserCreateRequest userCreateRequest) {
        User user = new User.Builder()
                .id(userCreateRequest.getUserId())
                .name(userCreateRequest.getName())
                .email(userCreateRequest.getEmail())
                .password(userCreateRequest.getPassword())
                .build();
        userService.join(user);
        return "redirect:/";
    }
}

