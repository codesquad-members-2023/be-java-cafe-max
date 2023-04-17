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

import kr.codesqaud.cafe.account.dto.ProfileEditDTO;
import kr.codesqaud.cafe.account.dto.UserDTO;
import kr.codesqaud.cafe.global.config.Session;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user/sign-up")
	public String signUp(@ModelAttribute @Valid UserDTO userDto) {
		userService.addUser(userDto);
		return "redirect:/user/list";
	}

	@GetMapping("/user/list")
	public String showUserList(Model model) {
		model.addAttribute("users", userService.getUserList());
		return "user/list";
	}

	@GetMapping("/user/profile/{id}")
	public String showUserProfile(@PathVariable String id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		return "user/profile";
	}

	@PutMapping("/user/profile/{id}")
	public String updateUserData(@ModelAttribute @Valid ProfileEditDTO profileEditDto, HttpSession httpSession) {
		userService.updateUser(profileEditDto);
		Session session = new Session(profileEditDto.getId(), profileEditDto.getNickName());
		httpSession.setAttribute(Session.LOGIN_USER, session);
		return "redirect:/user/list";
	}
}
