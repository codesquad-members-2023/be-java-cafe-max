package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UpdateFormDto;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/create")
    public String postSignUp(SignUpFormDto signUpFormDto) {
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
    public String getUpdateForm(@PathVariable String id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/update_form";
    }

    @PutMapping("/update/{id}")
    public String putUpdate(@PathVariable String id, UpdateFormDto updateFormDto) {
        userService.update(userService.findById(id), updateFormDto);
        return "redirect:/user";
    }

    @PostMapping("/signIn")
    public String signIn (@RequestParam("userId")String id, @RequestParam("password")String password,
                          HttpSession session){
        userService.login(id,password);
        session.setAttribute("sessionID",id);
        return "redirect:/";
    }


}


