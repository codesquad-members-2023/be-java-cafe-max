package kr.codesqaud.cafe.account;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.account.form.JoinForm;
import kr.codesqaud.cafe.account.form.LoginForm;
import kr.codesqaud.cafe.account.form.ProfileForm;
import kr.codesqaud.cafe.account.form.ProfileSettingForm;
import kr.codesqaud.cafe.account.form.UserForm;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users/login")
	public String showLoginPage(Model model, @Nullable @RequestParam boolean errors) {
		model.addAttribute("loginForm", new LoginForm());
		model.addAttribute("errors", errors);
		return "account/login";
	}

	@PostMapping("/users/login")
	public String login(@Valid LoginForm loginForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "account/login";
		}
		Optional<User> userOptional = userService.findByEmail(loginForm.getEmail());
		if (userOptional.isEmpty()) {
			bindingResult.rejectValue("email", "noExist", "이메일이 존재하지 않습니다.");
			return "account/login";
		}
		User user = userOptional.get();
		if (!user.getPassword().equals(loginForm.getPassword())) {
			bindingResult.rejectValue("password", "noMatch", "비밀번호가 일치하지 않습니다.");
			return "account/login";
		}
		return "redirect:/users/" + user.getId();
	}

	@GetMapping("/users/join")
	public String showJoinPage(Model model) {
		model.addAttribute("joinForm", new JoinForm());
		return "account/join";
	}

	@PostMapping("/users/join")
	public String addUser(@Valid JoinForm joinForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "account/join";
		}
		if (userService.containEmail(joinForm.getEmail())) {
			bindingResult.rejectValue("email", "duplicate", "중복된 이메일입니다.");
			return "account/join";
		}
		User user = userService.createNewUser(joinForm);
		return "redirect:/users/" + user.getId();
	}

	@GetMapping("/users")
	public String showUsers(Model model) {
		List<UserForm> allUserForm = userService.getAllUsersForm();
		model.addAttribute("users", allUserForm);
		return "account/members";
	}

	@GetMapping("/users/{userId}")
	public String showUser(Model model, @PathVariable Long userId) {
		Optional<User> userOptional = userService.findById(userId);
		if (userOptional.isEmpty()) {
			return "redirect:/";
		}
		ProfileForm profileForm = ProfileForm.from(userOptional.get());

		model.addAttribute("profileForm", profileForm);
		model.addAttribute("userId", userId);
		return "account/profile";
	}

	@GetMapping("/users/{userId}/update")
	public String showUserProfile(Model model, @PathVariable Long userId) {
		Optional<User> userOptional = userService.findById(userId);
		if (userOptional.isEmpty()) {
			return "redirect:/";
		}
		ProfileSettingForm profileSettingForm = ProfileSettingForm.from(userOptional.get());
		model.addAttribute("userId", userId);
		model.addAttribute("profileSettingForm", profileSettingForm);
		return "account/profileUpdate";
	}

	@PutMapping("/users/{userId}/update")
	public String setUserProfile(@Valid ProfileSettingForm profileSettingForm, Errors errors, @PathVariable Long userId
	) {
		Optional<User> userOptional = userService.findById(userId);
		if (userOptional.isEmpty()) {
			return "redirect:/";
		}
		if (errors.hasErrors()) {
			return "account/profileUpdate";
		}
		if (!userService.checkPasswordByUserId(profileSettingForm.getPassword(), userId)) {
			errors.rejectValue("password", "noMatch", "비밀번호가 일치하지 않습니다.");
			return "account/profileUpdate";
		}
		userService.update(profileSettingForm, userId);
		return "redirect:/users/{userId}";
	}
}
