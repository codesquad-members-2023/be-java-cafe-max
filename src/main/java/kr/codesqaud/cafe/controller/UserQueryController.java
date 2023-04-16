package kr.codesqaud.cafe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;

@Controller
public class UserQueryController {
	private final UserService userService;

	public UserQueryController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user/form")
	public String getSignUpForm() {
		return "user/form";
	}

	@GetMapping("/user/login")
	public String login() {
		return "user/login";
	}

	@GetMapping("/checkForUpdate")
	public String checkForUpdate() {
		return "user/checkForUpdate";
	}

	@GetMapping("/check/{userID}")
	public String checkUserID(@PathVariable String userID, HttpSession session) {
		Object value = session.getAttribute("sessionUser");
		if (value == null) {
			return "redirect:/user/login";
		}
		User user = (User)value;
		if (!user.getUserID().equals(userID)) {
			throw new IllegalStateException("다른 사용자의 정보는 수정할 수 없습니다.");
		}
		return "user/checkForUpdate";
	}

	@GetMapping("/user/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionUser");
		return "/";
	}

	@GetMapping("/users")
	public String getUserList(Model model) {
		List<User> users = userService.findUsers();
		model.addAttribute("users", users);
		return "user/list";
	}

	@GetMapping("/users/{userID}")
	public String getProfileByUserID(@PathVariable String userID, Model model) {
		User user = userService.findOne(userID);
		model.addAttribute("user", user);
		return "user/profile";
	}

	@GetMapping("/user/updateForm")
	public String updateForm(HttpSession session, Model model) {
		User user = (User)session.getAttribute("sessionUser");
		model.addAttribute("user", user);
		return "user/updateForm";
	}
}
