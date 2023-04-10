package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.controller.dto.req.LoginRequest;
import kr.codesqaud.cafe.service.AuthService;

@Controller
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public String login(@ModelAttribute final LoginRequest request, final HttpSession session) {
		session.setAttribute("sessionedUser", authService.login(request));
		return "redirect:/";
	}
}
