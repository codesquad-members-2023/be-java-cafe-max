package kr.codesqaud.cafe.app.user.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.app.user.controller.dto.UserResponse;
import kr.codesqaud.cafe.app.user.controller.dto.UserSavedRequest;
import kr.codesqaud.cafe.app.user.entity.User;
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
        List<UserResponse> userResponses =
            userService.getAllUsers().stream()
                .map(UserResponse::new)
                .collect(Collectors.toUnmodifiableList());
        mav.addObject("users", userResponses);
        return mav;
    }

    // 특정 회원 추가
    @PostMapping("/users")
    public UserResponse addUser(@Valid @RequestBody UserSavedRequest requestDto) {
        User user = userService.signUp(requestDto);
        return new UserResponse(user);
    }

    // 특정 회원 조회
    @GetMapping("/users/{id}")
    public ModelAndView detailUser(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("user/detail");
        mav.addObject("user", new UserResponse(userService.findUser(id)));
        return mav;
    }

    // 특정 회원 수정
    @PutMapping("/users/{id}")
    public UserResponse modifyUser(@PathVariable(value = "id") Long id,
        @Valid @RequestBody UserSavedRequest requestDto, HttpSession session) {
        // 현재 로그인한 사용자의 id와 url로 받은 id가 동일하지 않으면 예외 발생
        UserResponse loginUser = (UserResponse) session.getAttribute("user");
        if (!id.equals(loginUser.getId())) {
            throw new RestApiException(UserErrorCode.PERMISSION_DENIED);
        }

        User modifiedUser = userService.modifyUser(id, requestDto);
        // 회원정보 수정시 기존 세션에 저장되어 있는 유저 정보 제거
        session.removeAttribute("user");
        return new UserResponse(modifiedUser);
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
        User user = userService.findUser(id);
        UserResponse userResponse = new UserResponse(user);
        mav.addObject("user", userResponse);
        return mav;
    }
}
