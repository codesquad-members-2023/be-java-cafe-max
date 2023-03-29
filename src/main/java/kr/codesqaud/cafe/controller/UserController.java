package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/signup")
    public String signup() {
        return "/user/form";
    }

    @GetMapping("")
    public String userList(Model model) {
        model.addAttribute("users", service.findAllUsers());
        return "/user/list";
    }

    @PostMapping("")
    public String userAdd(SignUpDTO dto) {
        service.addUser(dto);
        return "redirect:/users";
    }

}
