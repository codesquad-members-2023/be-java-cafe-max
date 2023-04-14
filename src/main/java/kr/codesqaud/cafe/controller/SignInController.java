package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.config.Session;
import kr.codesqaud.cafe.controller.dto.SignInDTO;
import kr.codesqaud.cafe.controller.dto.user.UserDTO;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class SignInController {
    private final UserService userService;

    public SignInController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/sign-in-success")
    public String userSignIn(@ModelAttribute SignInDTO signInDto, HttpSession httpSession){
        String id = signInDto.getId();
        userService.matchPassword(signInDto);

        UserDTO userDto = userService.getUserById(id);
        Session session = new Session(userDto.getId(),userDto.getNickName());

        httpSession.setAttribute(Session.LOGIN_USER,session);
        return "redirect:/user/sign-in-success/"+id;
    }

    @GetMapping("/user/sign-in-success/{Id}")
    public String showSingInSuccessForm(@PathVariable String Id, Model model){
        model.addAttribute("user",userService.getUserById(Id));
        return "user/login_success";
    }

    @PostMapping("/user/sign-out")
    public String userSignOut(HttpSession request){
        request.invalidate();
        return "redirect:/";
    }
}
