package kr.codesqaud.cafe.account;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import kr.codesqaud.cafe.account.dto.ProfileEditRequest;
import kr.codesqaud.cafe.account.dto.UserSignUpRequest;
import kr.codesqaud.cafe.global.config.Session;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users/sign-up")
	public String signUp(@ModelAttribute @Valid UserSignUpRequest userSignUpRequest) {
		userService.addUser(userSignUpRequest);
		return "redirect:/users/list";
	}

	@GetMapping("/users/list")
	public String showUserList(Model model) {
		model.addAttribute("users", userService.getUserList());
		return "user/list";
	}

	@GetMapping("/users/profile/{id}")
	public String showUserProfile(@PathVariable String id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		return "user/profile";
	}

	@PutMapping("/users/profile/{id}")
	public String updateUserData(@ModelAttribute @Valid ProfileEditRequest profileEditRequest,
		HttpSession httpSession) {
		userService.updateUser(profileEditRequest);
		Session session = new Session(profileEditRequest.getId(), profileEditRequest.getNickName());
		httpSession.setAttribute(Session.LOGIN_USER, session);
		return "redirect:/users/list";
	}
}
