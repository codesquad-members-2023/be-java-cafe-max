package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.LoginDto;
import kr.codesqaud.cafe.dto.LoginSessionDto;
import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UpdateFormDto;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "user/form";
    }


    @PostMapping("/create")
    public String postSignUp(@Valid @ModelAttribute SignUpFormDto signUpFormDto) {
        userService.duplicatedId(signUpFormDto);
        userService.signUp(signUpFormDto);
        return "redirect:/user";
    }

    @GetMapping("")
    public String getUserList(Model model) {
        List<User> list = userService.getUserList();
        model.addAttribute("memberList", list);
        return "user/list";
    }

    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable String id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        return "user/profile";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable String id, Model model, HttpSession session) {
        LoginSessionDto sessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        userService.updateAccess(id, sessionDto);
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/update_form";
    }

    @PutMapping("/update/{id}")
    public String putUpdate(@PathVariable String id, @Valid @ModelAttribute UpdateFormDto updateFormDto, HttpSession session) {
        userService.update(userService.findById(id), updateFormDto);
        session.setAttribute("sessionId", new LoginSessionDto(id, updateFormDto.getName()));
        return "redirect:/user";
    }

    @PostMapping("/signIn")
    public String signIn(LoginDto dto, HttpSession session) {
        User user = userService.findById(dto.getUserId());
        userService.login(user, dto.getPassword());
        session.setAttribute("sessionId", new LoginSessionDto(user.getUserId(), user.getName()));
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
