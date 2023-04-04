package kr.codesqaud.cafe.account;

import static kr.codesqaud.cafe.utils.FiledName.*;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.codesqaud.cafe.account.form.JoinForm;
import kr.codesqaud.cafe.account.form.LoginForm;
import kr.codesqaud.cafe.account.form.ProfileForm;
import kr.codesqaud.cafe.account.form.ProfileSettingForm;
import kr.codesqaud.cafe.account.form.UserForm;
import kr.codesqaud.cafe.account.validator.JoinFormValidator;
import kr.codesqaud.cafe.account.validator.LoginFormValidator;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final LoginFormValidator loginFormValidator;
	private final JoinFormValidator joinFormValidator;

	public UserController(UserService userService, LoginFormValidator loginFormValidator,
		JoinFormValidator joinFormValidator) {
		this.userService = userService;
		this.loginFormValidator = loginFormValidator;
		this.joinFormValidator = joinFormValidator;
	}

	@InitBinder("loginForm")
	public void loginFormInitBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(loginFormValidator);
	}

	@InitBinder("joinForm")
	public void joinFormInitBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(joinFormValidator);
	}

	@GetMapping("/login")
	public String showLoginPage(Model model) {
		model.addAttribute(LOGIN_FORM, new LoginForm());
		return "account/login";
	}

	@PostMapping("/login")
	public String login(@Valid LoginForm loginForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "account/login";
		}
		Optional<User> userOptional = userService.findByEmail(loginForm.getEmail());
		return "redirect:/users/" + userOptional.get().getId();
	}

	@GetMapping("/join")
	public String showJoinPage(Model model) {
		model.addAttribute(JOIN_FORM, new JoinForm());
		return "account/join";
	}

	@PostMapping("/join")
	public String addUser(@Valid JoinForm joinForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "account/join";
		}
		User user = userService.createNewUser(joinForm);
		return "redirect:/users/" + user.getId();
	}

	@GetMapping
	public String showUsers(Model model) {
		List<UserForm> allUserForm = userService.getAllUsersForm();
		model.addAttribute(USERS, allUserForm);
		return "account/members";
	}

	@GetMapping("/{userId}")
	public String showUser(Model model, @PathVariable Long userId) {
		Optional<User> userOptional = userService.findById(userId);
		ProfileForm profileForm = ProfileForm.from(userOptional.get());

		model.addAttribute(PROFILE_FORM, profileForm);
		model.addAttribute(USER_ID, userId);
		return "account/profile";
	}

	@GetMapping("/{userId}/update")
	public String showUserProfile(Model model, @PathVariable Long userId) {
		Optional<User> userOptional = userService.findById(userId);
		ProfileSettingForm profileSettingForm = ProfileSettingForm.from(userOptional.get());
		model.addAttribute(USER_ID, userId);
		model.addAttribute(PROFILE_SETTING_FORM, profileSettingForm);
		return "account/profileUpdate";
	}

	@PutMapping("/{userId}/update")
	public String setUserProfile(@Valid ProfileSettingForm profileSettingForm, Errors errors, @PathVariable Long userId
	) {
		if (errors.hasErrors()) {
			return "account/profileUpdate";
		}
		Optional<User> userOptional = userService.findById(userId);
		User user = userOptional.get();
		if (userService.isDuplicateEmail(user.getEmail(), profileSettingForm.getEmail())) {
			errors.rejectValue(EMAIL, "error.email.duplicate");
			return "account/profileUpdate";
		}
		if (!userService.checkPassword(profileSettingForm.getPassword(), user.getPassword())) {
			errors.rejectValue(PASSWORD, "error.password.notMatch");
			return "account/profileUpdate";
		}
		userService.update(profileSettingForm, userId);
		return "redirect:/users/{userId}";
	}
}
