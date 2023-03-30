package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;
    @Autowired
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
        user.setName(form.getUserId());

        userService.join(user);
        return "redirect:/";
    }

}
