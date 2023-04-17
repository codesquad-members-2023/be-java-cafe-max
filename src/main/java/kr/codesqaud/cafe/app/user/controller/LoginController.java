package kr.codesqaud.cafe.app.user.controller;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.app.user.controller.dto.UserLoginRequest;
import kr.codesqaud.cafe.app.user.controller.dto.UserResponse;
import kr.codesqaud.cafe.app.user.entity.User;
import kr.codesqaud.cafe.app.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 페이지
    @GetMapping("/login")
    public ModelAndView loginForm() {
        return new ModelAndView("user/login");
    }

    // 로그인
    @PostMapping("/login")
    public ModelAndView login(@Valid @RequestBody UserLoginRequest requestDto,
        HttpSession session) {
        logger.info("login" + requestDto.toString());
        User user = userService.login(requestDto);
        session.setAttribute("user", new UserResponse(user));
        return new ModelAndView(new RedirectView("/"));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView("user/login");
        session.removeAttribute("user");
        return mav;
    }
}
