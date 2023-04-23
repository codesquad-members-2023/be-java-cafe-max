package kr.codesqaud.cafe.account;

import static kr.codesqaud.cafe.global.config.Session.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.codesqaud.cafe.account.dto.SignInRequest;
import kr.codesqaud.cafe.account.dto.UserSignUpRequest;
import kr.codesqaud.cafe.global.config.Session;

@Controller
public class AuthController {
	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users/sign-up")
	public String signUpForm() {
		return "user/form";
	}

	@PostMapping("/users/sign-up")
	public String signUp(@ModelAttribute @Valid UserSignUpRequest userSignUpRequest) {
		userService.addUser(userSignUpRequest);
		return "redirect:/users/list";
	}

	@GetMapping("/users/sign-in")
	public String signInForm() {
		return "user/sign-in";
	}

	@PostMapping("/users/sign-in")
	public String signIn(@ModelAttribute SignInRequest signInRequest, HttpSession httpSession) {
		String userId = signInRequest.getUserId();
		userService.matchPassword(signInRequest);

		Session session = new Session(userService.getUserById(userId));

		httpSession.setAttribute(Session.LOGIN_USER, session);
		return "redirect:/users/sign-in-success";
	}

	@GetMapping("/users/sign-in-success")
	public String signInSuccess(@SessionAttribute(LOGIN_USER) Session session, Model model) {
		model.addAttribute("user", userService.getUserById(session.getId()));
		return "user/sign-in-success";
	}

	@PostMapping("/users/sign-out")
	public String signOut(HttpSession request) {
		request.invalidate();
		return "redirect:/";
	}
}
