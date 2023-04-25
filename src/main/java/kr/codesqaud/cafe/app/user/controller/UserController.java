package kr.codesqaud.cafe.app.user.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.app.user.controller.dto.UserResponse;
import kr.codesqaud.cafe.app.user.controller.dto.UserSavedRequest;
import kr.codesqaud.cafe.app.user.service.UserService;
import kr.codesqaud.cafe.errors.errorcode.UserErrorCode;
import kr.codesqaud.cafe.errors.exception.RestApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 전체 회원 조회
    @GetMapping("/users")
    public ModelAndView listUser() {
        ModelAndView mav = new ModelAndView("user/list");
        mav.addObject("users", userService.getAllUsers());
        return mav;
    }

    // 특정 회원 추가
    @PostMapping("/users")
    public UserResponse createUser(@Valid @RequestBody UserSavedRequest userRequest) {
        return userService.signUp(userRequest);
    }

    // 특정 회원 조회
    @GetMapping("/users/{id}")
    public ModelAndView detailUser(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("user/detail");
        mav.addObject("user", userService.findUser(id));
        return mav;
    }

    // TODO : 인증 부분 인터셉터로 빼기
    // 특정 회원 수정
    @PutMapping("/users/{id}")
    public UserResponse modifyUser(@PathVariable(value = "id") Long id,
        @Valid @RequestBody UserSavedRequest userRequest, HttpSession session) {
        // 현재 로그인한 사용자의 id와 url로 받은 id가 동일하지 않으면 예외 발생
        UserResponse loginUser = (UserResponse) session.getAttribute("user");
        if (!id.equals(loginUser.getId())) {
            throw new RestApiException(UserErrorCode.PERMISSION_DENIED);
        }
        UserResponse modifiedUser = userService.modifyUser(id, userRequest);
        // 회원정보 수정시 기존 세션에 저장되어 있는 유저 정보 갱신
        session.setAttribute("user", modifiedUser);
        return modifiedUser;
    }

    // 회원가입 페이지
    @GetMapping("/users/new")
    public ModelAndView addUserForm() {
        return new ModelAndView("user/new");
    }

    // 회원수정 페이지
    @GetMapping("/users/{id}/edit")
    public ModelAndView modifyUserForm(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("user/edit");
        mav.addObject("user", userService.findUser(id));
        return mav;
    }
}
