package kr.codesqaud.cafe.account;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

	private final UserService userService;
	private final UsersRepository usersRepository;

	public UserController(UserService userService, UsersRepository usersRepository) {
		this.userService = userService;
		this.usersRepository = usersRepository;
	}

	@GetMapping("/users/login")
	public String showLoginPage(Model model, @Nullable @RequestParam boolean errors) {
		model.addAttribute(new UserForm());
		model.addAttribute("errors", errors);
		return "account/login";
	}

	@PostMapping("/users/login")
	public String login(UserForm userForm, RedirectAttributes model) {
		Optional<User> userOptional = usersRepository.findByEmail(userForm.getEmail());
		if (userOptional.isEmpty()) {
			model.addAttribute("errors", true);
			return "redirect:/users/login";
		}
		User user = userOptional.get();
		if (user.getPassword().equals(userForm.getPassword())) {
			model.addAttribute("errors", true);
			return "redirect:/users/login";
		}
		return "redirect:/users/" + user.getId();
	}

	@GetMapping("/users/join")
	public String showJoinPage(Model model) {
		model.addAttribute(new UserForm());
		return "account/join";
	}

	@PostMapping("/users")
	public String addUser(UserForm userForm) {
		User user = userService.createNewUser(userForm);
		return "redirect:/users/" + user.getId();
	}

	@GetMapping("/users")
	public String showUsers(Model model) {
		List<UserForm> allUsersForm = userService.getAllUsersForm();
		model.addAttribute("members", allUsersForm);
		return "account/members";
	}

	@GetMapping("/users/{userId}")
	public String showUser(Model model, @PathVariable Long userId) {
		Optional<UserForm> optionalUser = userService.findById(userId);
		UserForm userForm = optionalUser.get();
		model.addAttribute("user", userForm);
		model.addAttribute("userId", userId);
		return "account/profile";
	}

	@GetMapping("/users/{userId}/update")
	public String showUserForm(Model model, @PathVariable Long userId, @Nullable @RequestParam boolean errors) {
		Optional<UserForm> optionalUser = userService.findById(userId);
		UserForm userForm = optionalUser.get();
		model.addAttribute("userId", userId);
		model.addAttribute(userForm);
		model.addAttribute("errors", errors);
		return "account/profileUpdate";
	}

	@PutMapping("/users/{userId}/update")
	public String setUserForm(UserForm userForm, @PathVariable Long userId, RedirectAttributes model) {
		if (!userService.checkPasswordByUserId(userForm.getPassword(), userId)) {
			model.addAttribute("errors", true);
			return "redirect:/users/" + userId + "/update";
		}
		userService.update(userForm, userId);
		return "redirect:/users/{userId}";
	}
}
