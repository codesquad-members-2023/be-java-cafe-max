package kr.codesqaud.cafe.controller.user;

import kr.codesqaud.cafe.domain.dto.user.UserForm;
import kr.codesqaud.cafe.domain.dto.user.UserListForm;
import kr.codesqaud.cafe.domain.dto.user.UserProfileForm;
import kr.codesqaud.cafe.domain.dto.user.UserUpdateForm;
import kr.codesqaud.cafe.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    public String create(@Valid @ModelAttribute("userForm") UserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/form";
        }

        // 서비스에서 DTO 사용으로 User에 넣어줄 필요가 없어짐
        userService.join(form);
        return "redirect:/users/";
    }

    @GetMapping("/users")
    public String findList(Model model) {
        // DTO 사용으로 패스워드를 제외한 정보만 호출
        List<UserListForm> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
        // gradle -> 타임리프 ViewResolver가 이걸 해줌
        // "templates/" + "user/list" + ".html"
    }

    @GetMapping("/users/{userId}")
    public String findProfile(@PathVariable String userId, Model model) {
        // DTO 사용으로 프로필 정보만 호출
        UserProfileForm profile = userService.findProfile(userId);
        model.addAttribute("profile", profile);
        return "user/profile";
        // gradle -> 타임리프 ViewResolver가 이걸 해줌
        // "templates/" + "user/profile" + ".html"
    }

    @GetMapping("/users/{id}/updateForm")
    public String getUpdate(@PathVariable Long id, Model model) {
        // DTO 사용으로 업데이트 정보만 호출
        UserUpdateForm user = userService.findUpdate(id);
        model.addAttribute("userUpdated", user);
        return "user/updateForm";
    }

    @PutMapping("/users/{id}")
    public String putUpdate(@PathVariable Long id, @Valid UserUpdateForm updateUser, String existingPassword) {
        userService.updateUser(id, updateUser, existingPassword);
        return "redirect:/users";
    }
}
