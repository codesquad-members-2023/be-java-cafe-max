package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String createForm(){
        return "/user/form"; // template 폴더에 만들었다면
    }

    @PostMapping("/user/create")
    public String create(UserForm form){
        User user = new User();
        user.setUserId(form.getUserId());
        user.setPassword(form.getPassword());
        user.setName(form.getName());
        user.setEmail(form.getEmail());

        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model){
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/profile") // ("/users/{{userId}}")
    public String profile(){
        return "/user/profile";
    }

}
