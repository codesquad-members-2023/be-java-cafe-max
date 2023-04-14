package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.constant.SessionConst;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.SessionDto;
import kr.codesqaud.cafe.dto.UserJoinForm;
import kr.codesqaud.cafe.dto.UserLoginForm;
import kr.codesqaud.cafe.dto.UserUpdateForm;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String join(UserJoinForm form, RedirectAttributes redirectAttributes) {
        User user = new User(form.getUserId(), form.getPassword(), form.getName(), form.getEmail());

        boolean isSignUpSuccess = userService.join(user);

        redirectAttributes.addFlashAttribute(isSignUpSuccess ? "firstLogin" : "duplicateUserId", true);
        return "redirect:/user/" + (isSignUpSuccess ? "login" : "form");
    }

    @GetMapping("users")
    public String list(Model model, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) SessionDto loginUser) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        model.addAttribute("loginUser", loginUser);
        return "user/list";
    }

    @GetMapping("users/{userId}")
    public String profile(Model model, @PathVariable String userId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) SessionDto loginUser) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("loginUser", loginUser);
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
            User user = userService.findById(form.getUserId()).get();
            SessionDto sessionDto = new SessionDto(user.getUserId(), user.getName(), user.getEmail());
            session.setAttribute(SessionConst.LOGIN_MEMBER, sessionDto);
            return "redirect:/";
        }
        return "redirect:/user/login_failed";
    }

    @GetMapping ("user/logout")
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
    public String updateForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) SessionDto loginUser, Model model) {

        if (loginUser == null) {
            return "user/login";
        }
        User user = userService.findById(loginUser.getUserId()).get();
        model.addAttribute("user", user);
        model.addAttribute("loginUser", loginUser);
        return "user/update";
    }

    @PostMapping("user/update")
    public String update(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                             SessionDto loginUser, UserUpdateForm form, RedirectAttributes redirectAttributes) {

        User user = userService.findById(loginUser.getUserId()).get();
        if (!user.getPassword().equals(form.getPassword())) {
            redirectAttributes.addFlashAttribute("passwordIncorrect", true);
            return "redirect:/user/update";
        }
        userService.update(user, form.getName(), form.getEmail());
        redirectAttributes.addAttribute("userId", loginUser.getUserId());
        return "redirect:/users/{userId}";
    }
}
