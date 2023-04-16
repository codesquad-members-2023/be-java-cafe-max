package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.common.exception.CommonException;
import kr.codesqaud.cafe.common.exception.CommonExceptionType;
import kr.codesqaud.cafe.controller.dto.user.LoginUserSession;
import kr.codesqaud.cafe.controller.dto.user.UserJoinDto;
import kr.codesqaud.cafe.controller.dto.user.UserLoginDto;
import kr.codesqaud.cafe.controller.dto.user.UserUpdateDto;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequestMapping("/users")
@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String readUsers(Model model) {
        model.addAttribute("users", userService.findAll());

        return "user/list";
    }

    @GetMapping("/join")
    public String joinUser(Model model) {
        model.addAttribute("user", new UserJoinDto());

        return "user/form";
    }

    @PostMapping
    public String join(@Valid @ModelAttribute("user") UserJoinDto userJoinDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/form";
        }

        userService.join(userJoinDto);

        return "redirect:/users/login";
    }

    @GetMapping("/{id}")
    public String readUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.find(id));

        return "user/profile";
    }

    @GetMapping("/{id}/update")
    public String updateUserForm(@PathVariable Long id, Model model, @SessionAttribute("loginUser") LoginUserSession loginUserSession) {
        if (loginUserSession.isOtherUser(id)) {
            throw new CommonException(CommonExceptionType.ACCESS_DENIED);
        }
        model.addAttribute("user", new UserUpdateDto(userService.find(id)));

        return "user/update";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") UserUpdateDto userUpdateDto) {
        userService.update(userUpdateDto);

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginUser", new UserLoginDto());

        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginUser") UserLoginDto userLoginDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "user/login";
        }

        final LoginUserSession loginUser = userService.login(userLoginDto);

        final HttpSession session = request.getSession();

        session.setAttribute("loginUser", loginUser);

        return "redirect:/";
    }

    @GetMapping("{id}/logout")
    public String logout(@PathVariable Long id, HttpServletRequest request) {
        final HttpSession session = request.getSession();

        session.removeAttribute("loginUser");

        return "home";
    }
}
