package kr.codesqaud.cafe.web.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.web.dto.user.UserLoginRequestDto;
import kr.codesqaud.cafe.web.dto.user.UserResponseDto;
import kr.codesqaud.cafe.web.dto.user.UserSavedRequestDto;
import kr.codesqaud.cafe.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 전체 회원 조회
    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    // 특정 회원 조회
    @GetMapping("/users/{id}")
    public String profile(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", new UserResponseDto(userService.findUser(id)));
        return "user/profile";
    }

    // 특정 회원 추가
    @PostMapping("/users")
    @ResponseBody
    public UserResponseDto create(@Valid @RequestBody UserSavedRequestDto requestDto) {
        logger.info("create : " + requestDto.toString());
        return userService.signUp(requestDto);
    }

    @PostMapping("/users/{id}/update")
    public ModelAndView modify(@PathVariable(value = "id") Long id,
        @Valid @RequestBody UserSavedRequestDto requestDto) {
        logger.info(requestDto.toString());
        userService.modifyUser(id, requestDto);
        return new ModelAndView(new RedirectView("/users"));
    }

    // 로그인
    @PostMapping("/users/login")
    public String login(@Valid @RequestBody UserLoginRequestDto requestDto,
        HttpSession session) {
        logger.info("login" + requestDto.toString());
        userService.login(requestDto, session);
        return "redirect:/";
    }

    // 로그아웃
    @PostMapping("/users/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView("user/login");
        session.removeAttribute("user");
        return mav;
    }

    // 비밀번호 확인
    @PostMapping("/users/password/{id}")
    public String passwordConfirm(@PathVariable(value = "id") Long id,
        @Valid @RequestBody UserSavedRequestDto requestDto) {
        userService.confirmPassword(id, requestDto);
        return String.format("redirect:/user/form/%d", id);
    }

    // 회원가입 페이지
    @GetMapping("/user/form")
    public String createForm() {
        return "user/form";
    }

    // 회원수정 페이지
    @GetMapping("/user/form/{id}")
    public String modifyForm(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findUpdateUser(id));
        return "user/updateForm";
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String loginForm() {
        return "user/login";
    }

    // 비밀번호 확인 페이지
    @GetMapping("/user/password/{id}")
    public String passwordConfirmForm(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        return "user/passwordForm";
    }
}
