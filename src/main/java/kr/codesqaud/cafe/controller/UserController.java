package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.config.Session;
import kr.codesqaud.cafe.controller.dto.user.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.user.UserDTO;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/sign-up")
    public String signUp(@ModelAttribute @Valid UserDTO userDto) {
        userService.addUser(userDto);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    @GetMapping("/user/profile/{id}")
    public String showUserProfile(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/profile";
    }

    @PutMapping("/user/profile/{id}")
    public String updateUserData(@ModelAttribute @Valid ProfileEditDTO profileEditDto, HttpSession httpSession){
        userService.updateUser(profileEditDto);
        Session session = new Session(profileEditDto.getId(),profileEditDto.getNickName());
        httpSession.setAttribute(Session.LOGIN_USER,session);
        return "redirect:/user/list";
    }
}
