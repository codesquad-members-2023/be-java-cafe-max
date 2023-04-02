package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.UserSignUpDTO;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @GetMapping("/user/signUp")
    public String showSignUpForm(){
        return "/user/form";
    }

    @PostMapping("/user/signUp")
    public String signUp(UserSignUpDTO userSignUpDto) {
        userService.addUser(userSignUpDto.toUser());
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public String showUserProfile(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.getUserByUserId(id));
        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String userUpdateForm(@PathVariable String id,Model model){
        model.addAttribute("user",userService.getUserByUserId(id));
        return "/user/updateForm";
    }

    @PutMapping("/users/{id}")
    public String updateUserData(@PathVariable String id, ProfileEditDTO profileEditDto){
        userService.updateUserByUserId(profileEditDto,id);
        return "redirect:/users";
    }
}
