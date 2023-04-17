package codesquad.cafe.domain.user.controller;

import codesquad.cafe.domain.user.dto.UserRequestDto;
import codesquad.cafe.domain.user.dto.UserResponseDto;
import codesquad.cafe.domain.user.dto.UserUpdateRequestDto;
import codesquad.cafe.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String registerUser(@ModelAttribute @Valid final UserRequestDto userRequestDto) {
        userService.join(userRequestDto);
        return "redirect:/users";
    }

    @GetMapping
    public String showUsers(final Model model) {
        List<UserResponseDto> users = userService.showUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, final Model model) {
        UserResponseDto user = userService.findUser(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUpdateForm(@PathVariable String userId, final Model model) {
        model.addAttribute("userId", userId);
        return "user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId,
                             @ModelAttribute UserUpdateRequestDto userUpdateRequestDto) {
        userService.updateUser(userId, userUpdateRequestDto);
        return "redirect:/users";
    }

}
