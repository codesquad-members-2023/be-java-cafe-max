package kr.codesqaud.cafe.account;

import java.util.Objects;
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

	private final UsersRepository usersRepository = new UsersRepository();

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
		if (!Objects.equals(user.getPassword(), userForm.getPassword())) {
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
		User user = new User(User.createNewId());
		user.setNickname(userForm.getNickname());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());

		usersRepository.add(user);
		return "redirect:/users/" + user.getId();
	}

	@GetMapping("/users")
	public String showUsers(Model model) {
		model.addAttribute("members", usersRepository.getAllMembers());
		return "account/members";
	}

	@GetMapping("/users/{userId}")
	public String showUser(Model model, @PathVariable Long userId) {
		Optional<User> optionalUser = usersRepository.findById(userId);
		User user = optionalUser.get();
		model.addAttribute("user", user);
		return "account/profile";
	}

	@GetMapping("/users/{userId}/update")
	public String showUserForm(Model model, @PathVariable Long userId, @Nullable @RequestParam boolean errors) {
		Optional<User> optionalUser = usersRepository.findById(userId);
		User user = optionalUser.get();
		UserForm userForm = new UserForm();
		userForm.setNickname(user.getNickname());
		userForm.setEmail(user.getEmail());
		model.addAttribute("userId", userId);
		model.addAttribute(userForm);
		model.addAttribute("errors", errors);
		return "account/profileUpdate";
	}

	@PutMapping("/users/{userId}/update")
	public String setUserForm(UserForm userForm, @PathVariable Long userId, RedirectAttributes model) {
		Optional<User> optionalUser = usersRepository.findById(userId);
		User user = optionalUser.get();
		if (!user.getPassword().equals(userForm.getPassword())) {
			model.addAttribute("errors", true);
			return "redirect:/users/" + userId + "/update";
		}
		user.setEmail(userForm.getEmail());
		user.setNickname(userForm.getNickname());
		return "redirect:/users/{userId}";
	}
}
