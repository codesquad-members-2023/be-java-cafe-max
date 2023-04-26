package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.dto.UserRequest;
import kr.codesqaud.cafe.service.UserService;

@Controller
public class LogController {
	private final UserService userService;

	public LogController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String login() {
		return "user/login";
	}

	@PostMapping("/login")
	public String login(String userID, String password, HttpSession session) {
		UserRequest userRequest = userService.checkLogin(userID, password);
		session.setAttribute("sessionUser", userRequest);
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionUser");
		return "/";
	}
}
