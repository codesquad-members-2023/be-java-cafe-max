package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.user.UserForm;
import kr.codesqaud.cafe.controller.dto.user.UserProfileForm;
import kr.codesqaud.cafe.controller.dto.user.UserUpdateForm;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    // UserForm: 이러면 검증을 위한 dto인가??
    public String create(@Validated UserForm form) {
        User user = new User(form);
        userService.join(user);
        return "redirect:/users/" + user.getId();
    }

    @GetMapping("/users")
    public String findList(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
        // gradle -> 타임리프 ViewResolver가 이걸 해줌
        // "templates/" + "user/list" + ".html"
    }

    @GetMapping("/users/{id}")
    public String findProfile(@PathVariable("id") Long id, Model model) {
        UserProfileForm profile = userService.findProfile(id);
        model.addAttribute("profile", profile);
        return "user/profile";
        // gradle -> 타임리프 ViewResolver가 이걸 해줌
        // "templates/" + "user/profile" + ".html"
    }

    @GetMapping("/users/update/{id}")
    public String getUpdate(@PathVariable Long id, Model model) {
        UserUpdateForm userUpdated = userService.findUpdate(id);
        model.addAttribute("userUpdated", userUpdated);
        return "user/updateForm";
    }

    @PostMapping("/users/update/{id}")
    // USerUpdateForm: 검증을 위한 dto라 생각하고 userId 필드를 뺀 상태로 만들었습니다.
    public String postUpdate(@PathVariable Long id, @Validated UserUpdateForm form) {
        User user = new User(form);
        userService.updateUser(id, user);
        return "redirect:/users";
    }
}
