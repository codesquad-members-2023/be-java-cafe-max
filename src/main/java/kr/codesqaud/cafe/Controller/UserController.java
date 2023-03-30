package kr.codesqaud.cafe.Controller;

import kr.codesqaud.cafe.Dto.UserFormDto;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createUser(UserFormDto userForm) {
        userService.join(new User(userForm.getUserId(), userForm.getPassword(), userForm.getName(), userForm.getEmail()));
        return "redirect:/user";
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }


    @GetMapping("/{userId}")
    public String viewUserProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.getUserProfile(userId));
        return "user/profile";
    }


}
