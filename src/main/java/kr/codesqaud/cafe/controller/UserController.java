package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.user.JoinDTO;
import kr.codesqaud.cafe.controller.dto.user.ModifiedUserDTO;
import kr.codesqaud.cafe.controller.dto.user.ProfileDTO;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/new-try")
    public String signUp(@ModelAttribute final JoinDTO joinDTO, Model model) {
        boolean isExistUser = userService.checkDuplicate(joinDTO.getUserId());
        if(isExistUser) {
            model.addAttribute("duplicate", true);
            return "user/form";
        }
        userService.signUp(joinDTO);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(final Model model) {
        List<ProfileDTO> users = userService.findUsers();
        model.addAttribute("profileDTO", users);
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public String profileForm(@PathVariable final long id, final Model model) {
        ProfileDTO wantedUser = userService.findOne(id);
        model.addAttribute("profileDTO", wantedUser);
        return "user/profile";
    }

    @GetMapping("/users/{id}/revision")
    public String modifyProfileForm(@PathVariable final long id, final Model model) {
        boolean isDifferentUser = !userService.isOwner(id);
        if(isDifferentUser) {
            return "error/403";
        }
        ProfileDTO wantedUser = userService.findOne(id);
        model.addAttribute("profileDTO", wantedUser);
        return "user/modifyProfile";
    }

    @PutMapping("/users/{id}")
    public String modifyProfile(@PathVariable final long id, final ModifiedUserDTO modifiedUserDTO, final Model model) {
        boolean isPasswordWrong = !userService.isPasswordRight(id, modifiedUserDTO);
        if(isPasswordWrong) {
            model.addAttribute("passwordMismatch", true);
            model.addAttribute("profileDTO", userService.findOne(id));
            return "user/modifyProfile";
        }
        userService.modify(id, modifiedUserDTO);
        return "redirect:/users/" + id;
    }
}
