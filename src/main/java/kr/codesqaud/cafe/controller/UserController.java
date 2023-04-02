package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

	@GetMapping("users/{userId}")
	public String profile(Model model, @PathVariable String userId) {
		model.addAttribute("user", userService.userProfile(userId));
		return "user/profile";
	}
}
