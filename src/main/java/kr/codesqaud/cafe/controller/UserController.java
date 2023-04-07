package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.UserDTO;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
>>>>>>> feature3

import javax.validation.Valid;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signUp")
    public String showSignUpForm(){
        return "/user/form";
    }

<<<<<<< HEAD
    @PostMapping("/signUp")
=======
    @PostMapping("/users/signUp")
>>>>>>> feature3
    public String signUp(@Valid UserDTO userDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "/user/form";
        }
        userService.addUser(userDto);
        return "redirect:/users";
    }

    @GetMapping()
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showUserProfile(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String userUpdateForm(@PathVariable String id,Model model){
        model.addAttribute("user",userService.getUserById(id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUserData(@Valid ProfileEditDTO profileEditDto){
        userService.updateUserByUserId(profileEditDto);
        return "redirect:/users";
    }
}
