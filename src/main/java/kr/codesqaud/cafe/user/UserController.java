package kr.codesqaud.cafe.user;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.exception.user.UserNotFoundException;
import kr.codesqaud.cafe.user.dto.LoginRequestDto;
import kr.codesqaud.cafe.user.dto.ProfileResponseDto;
import kr.codesqaud.cafe.user.dto.SignUpRequestDto;
import kr.codesqaud.cafe.web.SessionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/new")
    public String signUpPage(SignUpRequestDto signUpRequestDto) {
        return "user/form";
    }

    @PostMapping("/users")
    public String create(@Valid final SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/form";
        }
        User user = signUpRequestDto.toEntity();
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String listPage(final Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String viewUserProfile(@PathVariable final String userId, final Model model) {
        User findUser = userService.findOne(userId).orElseThrow(UserNotFoundException::new);  // TODO: 예외 처리를 서비스에서 해야하는지 컨트롤러에서 해야하는지?
        ProfileResponseDto profileResponseDto = ProfileResponseDto.from(findUser);
        model.addAttribute("user", profileResponseDto);
        return "user/profile";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(
            @Valid LoginRequestDto loginRequestDto, BindingResult bindingResult, HttpServletRequest request) {
        User loginUser = userService.login(loginRequestDto.toEntity());

        if (bindingResult.hasErrors()) {
            logger.info("로그인 실패");
            return "user/login_failed";
        }

        if (loginUser == null) {
            logger.info("로그인 실패");
            return "user/login_failed";
        }
        logger.info("로그인 성공");

        HttpSession session = request.getSession();
        session.setAttribute(SessionConstant.LOGIN_USER_ID, loginUser.getLoginId());
        session.setAttribute(SessionConstant.LOGIN_USER_NAME, loginUser.getName());

        return "redirect:/";
    }

    @GetMapping("/logout") // TODO: Post로 리팩터링 필요
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
