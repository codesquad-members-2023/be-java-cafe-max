package kr.codesqaud.cafe.user;

import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/new")
    public String signUpPage() {
        return "user/form";
    }

    @PostMapping("/users")
    public String create(@Valid final SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // TODO: 에러 내용이 출력되게끔 로직 추가
            return "redirect:/error";
        }
        userService.join(signUpRequestDto);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String listPage(final Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String viewUserProfile(@PathVariable final String userId, final Model model) {
        User findUser = userService.findOne(userId).get();
        model.addAttribute("user", findUser);
        return "user/profile";
    }
}
