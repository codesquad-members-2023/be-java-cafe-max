package kr.codesqaud.cafe.account;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import kr.codesqaud.cafe.account.dto.ProfileEditRequest;
import kr.codesqaud.cafe.global.config.Session;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users/list")
	public String userList(Model model) {
		model.addAttribute("users", userService.getUserList());
		return "user/list";
	}

	@GetMapping("/users/{userId}")
	public String userProfile(@PathVariable String userId, Model model) {
		model.addAttribute("user", userService.getUserById(userId));
		return "user/profile";
	}

	@GetMapping("/users/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}

	@PutMapping("/users/{userId}")
	public String update(@ModelAttribute @Valid ProfileEditRequest profileEditRequest,
		HttpSession httpSession) {
		userService.updateUser(profileEditRequest);
		Session session = new Session(profileEditRequest.getUserId(), profileEditRequest.getNickName());
		httpSession.setAttribute(Session.LOGIN_USER, session);
		return "redirect:/users/list";
	}
}
