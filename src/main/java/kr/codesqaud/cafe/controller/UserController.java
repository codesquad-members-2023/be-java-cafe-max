package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.UserJoinDto;
import kr.codesqaud.cafe.controller.dto.UserReadDto;
import kr.codesqaud.cafe.controller.dto.UserUpdateDto;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/users")
@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String readUsers(Model model) {
        model.addAttribute("users", userService.findAll());

        return "user/list";
    }

    @PostMapping
    public String join(@Valid @ModelAttribute("user") UserJoinDto userJoinDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/form";
        }

        userService.join(userJoinDto);

        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String readUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.find(id));

        return "user/profile";
    }

    @GetMapping("/{id}/update")
    public String updateUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", new UserUpdateDto(userService.find(id)));

        return "user/update";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") UserUpdateDto userUpdateDto) {
        userService.update(userUpdateDto);

        return "redirect:/users";
    }
}
