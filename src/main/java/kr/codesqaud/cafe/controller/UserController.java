package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.controller.dto.LoginRequest;
import kr.codesqaud.cafe.controller.dto.SignUpRequest;
import kr.codesqaud.cafe.service.UserService;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user")
	public String signUp(@ModelAttribute SignUpRequest signUpRequest) {
		userService.userSignUp(signUpRequest);
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String getAllUsers(Model model) {
		model.addAttribute("users", userService.findAllUsers());
		return "user/list";
	}

	@GetMapping("/users/{userId}")
	public String profile(Model model, @PathVariable String userId) {
		model.addAttribute("user", userService.userProfile(userId));
		return "user/profile";
	}

	@PostMapping("/user/login")
	public String login(@ModelAttribute LoginRequest loginRequest, HttpSession session) {
		if (userService.userLogin(loginRequest)) {
			session.setAttribute("sessionedUser", loginRequest.getUserId());
			return "redirect:/";
		}
		return "user/login_failed";
	}

	@GetMapping("/users/{userId}/form")
	public String showModifyUserForm(Model model, @PathVariable String userId, HttpSession session) {
		Object value = session.getAttribute("sessionedUser");
		if (value != null && value.equals(userId)) {
			model.addAttribute("userId", userId);
			return "user/modify";
		}
		return "user/error";
	}
}
