package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.JoinDTO;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/join")
    public String joinForm() {
        return "user/form";
    }

    @PostMapping("/user/join")
    public String signUp(@ModelAttribute final JoinDTO joinDTO, Model model) {
        boolean isIdDuplicated = userService.checkDuplicate(joinDTO.getUserId());
        if(isIdDuplicated) {
            model.addAttribute("duplicate", true);
            return "user/form";
        }
        userService.signUp(joinDTO);
        return "redirect:/users/list";
    }

    @GetMapping("/users/list")
    public String list(final Model model) {
        List<JoinDTO> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public String profileForm(@PathVariable final long id, final Model model) {
        JoinDTO wantedUser = userService.findOne(id);
        model.addAttribute("wantedUser", wantedUser);
        return "user/profile";
    }

    @GetMapping("/users/modify/{id}")
    public String modifyProfileForm(@PathVariable final long id, final Model model) {
        JoinDTO wantedUser = userService.findOne(id);
        model.addAttribute("joinDTO", wantedUser);
        return "user/modifyProfile";
    }

    @PostMapping("/users/modify/{id}")
    public String modifyProfile(@PathVariable final long id, final JoinDTO joinDTO) {
        userService.modify(id, joinDTO);
        return "redirect:/users/" + id;
    }
}
