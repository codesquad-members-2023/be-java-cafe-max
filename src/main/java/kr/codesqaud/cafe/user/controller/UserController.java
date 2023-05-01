package kr.codesqaud.cafe.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.codesqaud.cafe.user.dto.SessionUser;
import kr.codesqaud.cafe.user.dto.UserAddForm;
import kr.codesqaud.cafe.user.dto.UserLoginForm;
import kr.codesqaud.cafe.user.dto.UserUpdateForm;
import kr.codesqaud.cafe.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Api(tags = {"회원 API"})
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "회원가입")
    @PostMapping
    public String addUser(@ModelAttribute UserAddForm userAddForm, HttpSession session) {
        String userId = userService.addUser(userAddForm);
        session.setAttribute("sessionUser", new SessionUser(userAddForm.getUserId(), userAddForm.getUserName()));
        return "redirect:/users";
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserLoginForm userLoginForm, HttpSession session) {
        session.setAttribute("sessionUser", userService.loginCheck(userLoginForm));
        return "redirect:/board";
    }

    @ApiOperation(value = "로그아웃")
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.removeAttribute("sessionUser");
        return "user/login";
    }

    @ApiOperation(value = "회원 목록")
    @GetMapping
    public String getUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    @ApiOperation(value = "회원 상세조회")
    @GetMapping("/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.getUser(userId));
        return "user/profile";
    }

    @ApiOperation(value = "회원 정보 수정 페이지 이동")
    @GetMapping("/update")
    public String viewUpdateForm(HttpSession session, Model model) {
        String sessionUserId = ((SessionUser) session.getAttribute("sessionUser")).getUserId();
        model.addAttribute("user", userService.getUser(sessionUserId));
        return "user/update";
    }

    @ApiOperation(value = "회원 정보 수정")
    @PutMapping
    public String updateUser(@ModelAttribute UserUpdateForm userUpdateForm, HttpSession session) {
        session.setAttribute("sessionUser", userService.updateUser(userUpdateForm));
        return "redirect:/users";
    }

}
