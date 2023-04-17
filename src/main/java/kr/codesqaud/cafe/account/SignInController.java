package kr.codesqaud.cafe.account;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.account.dto.SignInRequest;
import kr.codesqaud.cafe.account.dto.UserResponse;
import kr.codesqaud.cafe.global.config.Session;

@Controller
public class SignInController {
	private final UserService userService;

	public SignInController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users/sign-in")
	public String userSignIn(@ModelAttribute SignInRequest signInRequest, HttpSession httpSession) {
		String id = signInRequest.getId();
		userService.matchPassword(signInRequest);

		UserResponse userDto = userService.getUserById(id);
		Session session = new Session(userDto.getId(), userDto.getNickName());

		httpSession.setAttribute(Session.LOGIN_USER, session);
		return "redirect:/users/sign-in/" + id;
	}

	@GetMapping("/users/sign-in/{id}")
	public String showSingInSuccessForm(@PathVariable String id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		return "user/sign-in-success";
	}

	@PostMapping("/users/sign-out")
	public String userSignOut(HttpSession request) {
		request.invalidate();
		return "redirect:/";
	}
}
