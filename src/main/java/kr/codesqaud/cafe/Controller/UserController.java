package kr.codesqaud.cafe.Controller;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String createUser(UserForm userForm){
        users.add(new User(userForm.getUserId(), userForm.getPassword(), userForm.getName(), userForm.getEmail()));
        return "redirect:/users";
    }



}
