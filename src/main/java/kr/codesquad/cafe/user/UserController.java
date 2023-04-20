package kr.codesquad.cafe.user;

import kr.codesquad.cafe.user.domain.User;
import kr.codesquad.cafe.user.dto.JoinForm;
import kr.codesquad.cafe.user.dto.LoginForm;
import kr.codesquad.cafe.user.dto.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private static final String ATTRIBUTE_USER = "user";
    private static final int DEFAULT_PAGE = 0;
    private static final String USERS = "users";
    private final UserService userService;
    private final JoinFormValidator joinFormValidator;

    public UserController(UserService userService, JoinFormValidator joinFormValidator) {
        this.userService = userService;
        this.joinFormValidator = joinFormValidator;
    }


    @InitBinder(value = "joinForm")
    public void joinFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinFormValidator);
    }

    @GetMapping("/users/login")
    public String viewLoginForm(@ModelAttribute LoginForm loginForm) {
        return "user/login";
    }

    @PostMapping("/users/login")
    public String login(@Valid LoginForm loginForm, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "user/login";
        }
        User user = userService.checkLoginForm(loginForm);
        session.setAttribute(ATTRIBUTE_USER, user);
        return "redirect:/users/" + user.getId();
    }

    @GetMapping("/users/logout")
    public String logout(@ModelAttribute LoginForm loginForm, HttpSession session) {
        session.removeAttribute(ATTRIBUTE_USER);
        return "user/login";
    }

    @GetMapping("/users/join")
    public String viewJoinForm(@ModelAttribute JoinForm joinForm) {
        return "user/join";
    }

    @PostMapping("/users")
    public String saveUser(@Valid JoinForm joinForm, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "user/join";
        }
        User user = userService.save(joinForm);
        session.setAttribute(ATTRIBUTE_USER, user);
        return "redirect:/users/" + user.getId();
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        List<UserForm> allUserForm = userService.getAllUsersForm(DEFAULT_PAGE);
        model.addAttribute(USERS, allUserForm);
        return "user/users";
    }


}
