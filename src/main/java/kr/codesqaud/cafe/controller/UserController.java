package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.UserDTO;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.service.UserService;
import kr.codesqaud.cafe.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final MemoryUserRepository memoryUserRepository;

    @Autowired
    public UserController(MemoryUserRepository memoryUserRepository) {
        this.memoryUserRepository = memoryUserRepository;
    }

    @GetMapping("/user/form")
    public String join() {
        return "user/form";
    }

    @GetMapping
    public String listAllUsers(Model model){
        List<User> users = memoryUserRepository.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @PostMapping("/user/create")
    public String create(UserDTO userDTO) {
        User user = new User(userDTO.getUserId(), userDTO.getPassword(),
                userDTO.getName(), userDTO.getEmail());
        memoryUserRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String listPage(final Model model) {
        List<User> users = memoryUserRepository.findAll();
        model.addAttribute("users", users);
        return "/user/list";
    }

//    @GetMapping("/users/{userId}")
//    public String viewUserProfile(@PathVariable final String userId, final Model model) {
//        User findUser = userService.findOne(userId).get();
//        model.addAttribute("user", findUser);
//        return "/user/profile";
//    }
}
