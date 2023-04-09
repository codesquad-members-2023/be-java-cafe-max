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

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    public String create(@Validated UserForm form) {
        User user = new User(form.getUserId(), form.getPassword(), form.getName(), form.getEmail());
        userService.join(user);
        return "redirect:/users/" + user.getId();
    }

    @GetMapping("/users")
    public String findList(Model model) {
        // 회원 정보를 다 넘겨주고 th:each문으로 전체를 출력 (DTO 사용 X)
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
        // gradle -> 타임리프 ViewResolver가 이걸 해줌
        // "templates/" + "user/list" + ".html"
    }

    @GetMapping("/users/{id}")
    public String findProfile(@PathVariable Long id, Model model) {
        // DTO 사용으로 프로필 정보만 호출
        UserProfileForm profile = userService.findProfile(id);
        model.addAttribute("profile", profile);
        return "user/profile";
        // gradle -> 타임리프 ViewResolver가 이걸 해줌
        // "templates/" + "user/profile" + ".html"
    }

    @GetMapping("/users/update/{id}")
    public String getUpdate(@PathVariable Long id, Model model) {
        // DTO 사용으로 업데이트 정보만 호출
        UserUpdateForm user = userService.findUpdate(id);
        model.addAttribute("userUpdated", user);
        return "user/updateForm";
    }

    @PostMapping("/users/update/{id}")
    public String postUpdate(@PathVariable Long id, @Valid UserUpdateForm updateUser) {
        userService.updateUser(id, updateUser);
        return "redirect:/users";
    }
}
