package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.config.Session;
import kr.codesqaud.cafe.controller.dto.LoginDTO;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/login-success")
    public String userLogin(@ModelAttribute LoginDTO loginDto,HttpServletRequest request){
        String userId = loginDto.getUserId();
        userService.matchPassword(loginDto);
        HttpSession session = request.getSession();
        session.setAttribute(Session.LOGIN_MEMBER,userId);
        return "redirect:/user/login-success/"+userId;
    }

    @GetMapping("/user/login-success/{userId}")
    public String showLoginSuccessForm(@PathVariable String userId,Model model){
        model.addAttribute("user",userService.getUserById(userId));
        return "user/login_success";
    }
}
