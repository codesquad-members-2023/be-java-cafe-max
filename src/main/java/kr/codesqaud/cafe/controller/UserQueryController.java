package kr.codesqaud.cafe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.DeniedDataModificationException;
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

	@GetMapping("/user/checkForUpdate")
	public String checkForUpdate(HttpSession session) {
		boolean passwordCheck = true;
		session.setAttribute("passwordCheck", passwordCheck);
		return "user/checkForUpdate";
	}

	@GetMapping("/check/{userID}")
	public String checkUserID(@PathVariable String userID, HttpSession session) {
		User user = (User)session.getAttribute("sessionUser");
		user.validateUserId(userID);
		if (session.getAttribute("passwordCheck") == null) {
			throw new DeniedDataModificationException("잘못된 접근입니다.");
		}
		session.removeAttribute("passwordCheck");
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

	@GetMapping("/users/{nickname}")
	public String getProfileByUserID(@PathVariable String nickname, Model model) {
		User user = userService.findByNickname(nickname);
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
