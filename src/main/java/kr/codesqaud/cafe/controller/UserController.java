package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.UserSignUpRequest;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @RequestMapping("/user/signup")
    public String join(HttpServletRequest request, @ModelAttribute UserSignUpRequest userSignUpRequest) {

        //TODO Dto -> Entity 메서드
        if ("POST".equals(request.getMethod())) {

            userService.join(userSignUpRequest);

            return "redirect:/users";
        }

        return "user/form";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.findUsers());

        return "user/list";
    }

    @GetMapping("/user/{userId}")
    public String showUserProfile(
            @PathVariable("userId") String userId
            , Model model
    ) {
        model.addAttribute("userProfile", userService.findByUserId(userId));

        return "user/profile";
    }

    @GetMapping("/user/{userId}/update")
    public String showPasswordEditForm(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", userService.findByUserId(userId));

        return "user/form_update";
    }

    @PutMapping("/user/{userId}/update")
    public String updatePassword(
            @PathVariable("userId") String userId,
            @RequestParam("newPassword") String newPassword
    ) {
        userService.updateUserPassword(userId, newPassword);

        return "redirect:/users";
    }
}
