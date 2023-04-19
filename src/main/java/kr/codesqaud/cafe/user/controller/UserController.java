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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String addUser(@ModelAttribute UserAddForm userAddForm, HttpSession session) {
        String userId = userService.addUser(userAddForm);
        session.setAttribute("sessionUser", new SessionUser(userAddForm.getUserId(), userAddForm.getUserName()));
        return "redirect:/user/list";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserLoginForm userLoginForm, HttpSession session) {
        session.setAttribute("sessionUser", userService.loginCheck(userLoginForm));
        return "redirect:/board/list";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.removeAttribute("sessionUser");
        return "user/login";
    }

    @GetMapping("/list")
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
        if (!userService.checkPassword(userUpdateForm)) {
            model.addAttribute("error_password", true);
            model.addAttribute("user", userUpdateForm);
            return "user/update";
        }

        if (!userService.updateUser(userUpdateForm)) {
            model.addAttribute("error_duplicateName", true);
            model.addAttribute("user", userUpdateForm);
            return "user/update";
        }

        return "redirect:/user/list";
    }

}
