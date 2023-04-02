package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.UserForm;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    public String create(UserForm form) {
        User user = new User(form.getUserId(), form.getPassword(), form.getName(), form.getEmail());

        userService.join(user);

        return "redirect:/users/" + user.getUserId();
    }

    @GetMapping("/users")
    public String findList(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
        // gradle -> 타임리프 ViewResolver가 이걸 해줌
        // "templates/" + "user/list" + ".html"
    }

    @GetMapping("/users/{userId}")
    public String findProfile(@PathVariable("userId") String userId, Model model) {
        User user;
        if (userService.findByUserId(userId).isPresent()) {
            user = userService.findByUserId(userId).get();
        } else {
            throw new NoSuchElementException();
        }

        model.addAttribute("user", user);
        return "user/profile";
        // gradle -> 타임리프 ViewResolver가 이걸 해줌
        // "templates/" + "user/profile" + ".html"
    }

    @GetMapping("/users/update/{userId}")
    public String getUpdate(@PathVariable String userId, Model model) {
        User user = userService.findByUserId(userId).get();
        model.addAttribute("userUpdated", user);
        return "user/updateForm";
    }

    @PutMapping("/users/update/{userId}")
    public String putUpdate(@PathVariable String userId, User user) {
        userService.updateUser(userId, user);
        return "redirect:/users";
    }
}
