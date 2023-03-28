package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.controller.dto.req.JoinRequest;
import kr.codesqaud.cafe.service.UserService;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user/create")
	public String showJoinForm() {
		return "user/form";
	}

	@PostMapping("/user/create")
	public String join(@ModelAttribute final JoinRequest request) {
		userService.join(request);
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String showAllUsers(final Model model) {
		model.addAttribute("users", userService.getUsers());
		return "user/list";
	}

	@GetMapping("/users/{userId}")
	public String showProfilePage(@PathVariable final String userId, final Model model) {
		model.addAttribute("user", userService.findByUserId(userId));
		return "user/profile";
	}
}
