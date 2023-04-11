package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.SessionConst;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserJoinForm;
import kr.codesqaud.cafe.dto.UserLoginForm;
import kr.codesqaud.cafe.dto.UserUpdateForm;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user/form")
    public String joinForm() {
        return "user/form";
    }

    @PostMapping("user/create")
    public String create(UserJoinForm form) {
        User user = new User(form.getUserId(), form.getPassword(), form.getName(), form.getEmail());

        boolean isSignUpSuccess = userService.join(user);

        if (isSignUpSuccess) {
            return "redirect:/users";
        }
        return "redirect:/user/form";

    }

    @GetMapping("users")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("users/{userId}")
    public String profile(Model model, @PathVariable String userId) {
        model.addAttribute("user", userService.findOne(userId));
        return "user/profile";
    }

    @GetMapping("user/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("user/login")
    public String login(UserLoginForm form, HttpServletRequest request) {

        boolean isLoginSuccess = userService.login(form.getUserId(), form.getPassword());

        if (isLoginSuccess) {

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, userService.findOne(form.getUserId()).get());
            return "redirect:/";
        }
        return "redirect:/user/login_failed";
    }

    @GetMapping("user/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("user/login_failed")
    public String failLogin() {
        return "user/login_failed";
    }

    @GetMapping("user/update")
    public String updateForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginUser) {

        if (loginUser == null) {
            return "user/login";
        }
        return "user/update";
    }

    @PostMapping("user/update")
    public String update(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                             User loginUser, UserUpdateForm form) {

        if (!loginUser.getPassword().equals(form.getPassword())) {
            return "redirect:/user/update";
        }
        userService.update(loginUser, form.getName(), form.getEmail());

        return "redirect:/";
    }
}
