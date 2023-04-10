package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.UserDTO;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid UserDTO userDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "user/form";
        }
        userService.addUser(userDto);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    @GetMapping("/profile/{id}")
    public String showUserProfile(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/profile";
    }

    @GetMapping("/profile/{id}/form")
    public String userUpdateForm(@PathVariable String id,Model model){
        model.addAttribute("user",userService.getUserById(id));
        return "user/updateForm";
    }

    @PutMapping("/profile/{id}")
    public String updateUserData(@Valid ProfileEditDTO profileEditDto){
        userService.updateUserById(profileEditDto);
        return "redirect:/users";
    }
}
