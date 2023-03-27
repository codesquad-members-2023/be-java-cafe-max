package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
