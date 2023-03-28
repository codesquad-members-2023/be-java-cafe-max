package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.user.MemoryUserRepository;
import kr.codesqaud.cafe.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping(value = "/form")
    public String form() {
        return "user/form";
    }

    @PostMapping("/form")
    public String post(
            @RequestParam("userId") String userId,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String email) {

        MemoryUserRepository memoryUserRepository = new MemoryUserRepository();
        User user = new User(userId, password, name, email);
        memoryUserRepository.save(user);

        System.out.println("userId = " + userId);
        System.out.println("password = " + password);
        System.out.println("name = " + name);
        System.out.println("email = " + email);

        return "redirect:/user/list";
    }

    @GetMapping(value = "/list")
    public String joinSuccess() {
        return "user/list";
    }
}