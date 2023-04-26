package kr.codesqaud.cafe.user.controller;

import kr.codesqaud.cafe.user.dto.SessionUser;
import kr.codesqaud.cafe.user.dto.UserAddForm;
import kr.codesqaud.cafe.user.dto.UserLoginForm;
import kr.codesqaud.cafe.user.dto.UserUpdateForm;
import kr.codesqaud.cafe.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String addUser(@ModelAttribute UserAddForm userAddForm, HttpSession session) {
        String userId = userService.addUser(userAddForm);
        session.setAttribute("sessionUser", new SessionUser(userAddForm.getUserId(), userAddForm.getUserName()));
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserLoginForm userLoginForm, HttpSession session) {
        session.setAttribute("sessionUser", userService.loginCheck(userLoginForm));
        return "redirect:/board";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.removeAttribute("sessionUser");
        return "user/login";
    }

    @GetMapping
    public String getUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.getUser(userId));
        return "user/profile";
    }

    @GetMapping("/update")
    public String viewUpdateForm(HttpSession session, Model model) {
        String sessionUserId = ((SessionUser) session.getAttribute("sessionUser")).getUserId();
        model.addAttribute("user", userService.getUser(sessionUserId));
        return "user/update";
    }

    @PutMapping
    public String updateUser(@ModelAttribute UserUpdateForm userUpdateForm, Model model) {
        userService.updateUser(userUpdateForm);
        return "redirect:/users";
    }

}
