package kr.codesqaud.cafe.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	private final UsersRepository usersRepository = new UsersRepository();

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
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String showUsers(Model model) {
		model.addAttribute("members", usersRepository.getAllMembers());
		return "account/members";
	}
}
