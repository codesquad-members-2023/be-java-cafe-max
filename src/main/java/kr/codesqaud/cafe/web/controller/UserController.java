package kr.codesqaud.cafe.web.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.web.dto.user.UserLoginRequestDto;
import kr.codesqaud.cafe.web.dto.user.UserResponseDto;
import kr.codesqaud.cafe.web.dto.user.UserSavedRequestDto;
import kr.codesqaud.cafe.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/form")
    public String form() {
        return "user/form";
    }

    @PostMapping("/user/create")
    @ResponseBody
    public UserResponseDto newUser(@Valid @RequestBody UserSavedRequestDto requestDto) {
        logger.info(requestDto.toString());
        return userService.signUp(requestDto);
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public String profile(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", new UserResponseDto(userService.findUser(id)));
        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String updateForm(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findUpdateUser(id));
        return "user/updateForm";
    }

    @PostMapping("/users/{id}/update")
    public String update(@PathVariable(value = "id") Long id,
        @Valid @RequestBody UserSavedRequestDto requestDto) {
        logger.info(requestDto.toString());
        userService.updateUser(id, requestDto);
        return "redirect:/users";
    }

    @GetMapping("/user/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/user/login")
    public String login(@Valid @RequestBody UserLoginRequestDto requestDto,
        HttpSession session) {
        logger.info(requestDto.toString());
        userService.login(requestDto, session);
        return "redirect:/";
    }

    @GetMapping("/user/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView("user/login");
        session.removeAttribute("user");
        return mav;
    }

    @GetMapping("/user/{id}/password")
    public String passwordForm(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        return "user/passwordForm";
    }

    @PostMapping("/user/{id}/password")
    public String passwordConfirm(@PathVariable(value = "id") Long id,
        @Valid @RequestBody UserSavedRequestDto requestDto) {
        userService.confirmPassword(id, requestDto);
        return String.format("redirect:/users/%d/form", id);
    }
}
