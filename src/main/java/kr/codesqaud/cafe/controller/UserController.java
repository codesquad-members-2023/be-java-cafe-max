package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.UserJoinDTO;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
public class UserController {

    UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @GetMapping("/user/join")
    public String showJoinFrom(){
        return "/user/form";
    }

    @PostMapping("/user/join")
    public String join(UserJoinDTO userJoinDto) {
        userService.addUser(userJoinDto.toUser());
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public String showUserProfile(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUserByUserId(id));
        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String userUpdateForm(@PathVariable int id,Model model){
        model.addAttribute("user",userService.getUserByUserId(id));
        return "/user/updateForm";
    }

    @PutMapping("/users/{id}/update")
    public String updateUserData(@PathVariable int id, ProfileEditDTO profileEditDto){
        userService.updateUserByUserId(profileEditDto,id);
        return "redirect:/users";
    }

}
