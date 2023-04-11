package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.dto.UserFormDto;
import kr.codesqaud.cafe.user.service.UserService;
import kr.codesqaud.cafe.utils.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    //회원 가입 시, 회원 객체 생성 후 join
    @PostMapping("/create")
    public String createUser(UserFormDto userForm) {
        userService.join(new User(userForm.getUserId(), userForm.getPassword(), userForm.getName(), userForm.getEmail()));
        return "redirect:/user";
    }

    //회원 목록 표시
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }


    //회원 프로필 표시
    @GetMapping("/{userId}")
    public String viewUserProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.getUserProfile(userId));
        return "user/profile";
    }

    @PostMapping("/session-login")
    public String logIn( String userId, String password, HttpSession session){
        User user = userService.findAndAuthenticate(userId, password);

        if (user == null) {
            // Return error message
            return "redirect:/login?error";
        }

        Session.logIn(session, user);
        return "redirect:/";
    }

    @GetMapping("/session-logout")
    public String logOut(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
