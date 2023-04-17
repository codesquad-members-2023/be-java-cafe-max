package kr.codesqaud.cafe.app.user.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.app.user.controller.dto.UserLoginRequest;
import kr.codesqaud.cafe.app.user.controller.dto.UserResponse;
import kr.codesqaud.cafe.app.user.controller.dto.UserSavedRequest;
import kr.codesqaud.cafe.app.user.controller.dto.UserModifiedResponse;
import kr.codesqaud.cafe.app.user.entity.User;
import kr.codesqaud.cafe.app.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 전체 회원 조회
    @GetMapping("/users")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("user/list");
        List<UserResponse> userResponses =
            userService.getAllUsers().stream()
                .map(UserResponse::new)
                .collect(Collectors.toUnmodifiableList());
        mav.addObject("users", userResponses);
        return mav;
    }

    // 특정 회원 조회
    @GetMapping("/users/{id}")
    public ModelAndView profile(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("user/profile");
        mav.addObject("user", new UserResponse(userService.findUser(id)));
        return mav;
    }

    // 특정 회원 추가
    @PostMapping("/users")
    public UserResponse create(@Valid @RequestBody UserSavedRequest requestDto) {
        logger.info("create : " + requestDto.toString());
        User user = userService.signUp(requestDto);
        return new UserResponse(user);
    }

    // 특정 회원 수정
    @PutMapping("/users/{id}/update")
    public UserResponse modify(@PathVariable(value = "id") Long id,
        @Valid @RequestBody UserSavedRequest requestDto, HttpSession session) {
        logger.info(requestDto.toString());

        // 회원정보 수정시 기존 세션에 저장되어 있는 유저 정보 제거
        session.removeAttribute("user");
        User user = userService.modifyUser(id, requestDto);
        return new UserResponse(user);
    }

    // 로그인
    @PostMapping("/users/login")
    public ModelAndView login(@Valid @RequestBody UserLoginRequest requestDto,
        HttpSession session) {
        logger.info("login" + requestDto.toString());
        User user = userService.login(requestDto);
        session.setAttribute("user", new UserResponse(user));
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
        @Valid @RequestBody UserSavedRequest requestDto) {
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
        User user = userService.findUser(id);
        UserModifiedResponse userResponse = new UserModifiedResponse(user);
        mav.addObject("user", userResponse);
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
