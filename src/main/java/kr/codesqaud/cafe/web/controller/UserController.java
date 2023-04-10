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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 전체 회원 조회
    @GetMapping("/users")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("user/list");
        mav.addObject("users", userService.getAllUsers());
        return mav;
    }

    // 특정 회원 조회
    @GetMapping("/users/{id}")
    public ModelAndView profile(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("user/profile");
        mav.addObject("user", new UserResponseDto(userService.findUser(id)));
        return mav;
    }

    // 특정 회원 추가
    @PostMapping("/users")
    public UserResponseDto create(@Valid @RequestBody UserSavedRequestDto requestDto) {
        logger.info("create : " + requestDto.toString());
        return userService.signUp(requestDto);
    }

    // 특정 회원 수정, TODO: PUT 메소드 방식으로 변경하기
    @RequestMapping(value = "/users/{id}/update", produces = "application/json", method = RequestMethod.PUT)
    public UserResponseDto modify(@PathVariable(value = "id") Long id,
        @Valid @RequestBody UserSavedRequestDto requestDto) {
        logger.info(requestDto.toString());
        return userService.modifyUser(id, requestDto);
    }

    // 로그인
    @PostMapping("/users/login")
    public ModelAndView login(@Valid @RequestBody UserLoginRequestDto requestDto,
        HttpSession session) {
        logger.info("login" + requestDto.toString());
        userService.login(requestDto, session);
        return new ModelAndView(new RedirectView("/"));
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
    public ModelAndView passwordConfirm(@PathVariable(value = "id") Long id,
        @Valid @RequestBody UserSavedRequestDto requestDto) {
        userService.confirmPassword(id, requestDto);
        return new ModelAndView(new RedirectView("/user/form/" + id));
    }

    // 회원가입 페이지
    @GetMapping("/user/form")
    public ModelAndView createForm() {
        return new ModelAndView("user/form");
    }

    // 회원수정 페이지
    @GetMapping("/user/form/{id}")
    public ModelAndView modifyForm(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("user/updateForm");
        mav.addObject("user", userService.findUpdateUser(id));
        return mav;
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public ModelAndView loginForm() {
        return new ModelAndView("user/login");
    }

    // 비밀번호 확인 페이지
    @GetMapping("/user/password/{id}")
    public ModelAndView passwordConfirmForm(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("user/passwordForm");
        mav.addObject("user", userService.findUser(id));
        return mav;
    }
}
