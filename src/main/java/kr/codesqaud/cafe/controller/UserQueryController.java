package kr.codesqaud.cafe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.codesqaud.cafe.dto.UserRequest;
import kr.codesqaud.cafe.dto.UserResponse;
import kr.codesqaud.cafe.exception.InvalidAccessException;
import kr.codesqaud.cafe.service.UserService;

@Controller
public class UserQueryController {
	private final UserService userService;

	public UserQueryController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users/form")
	public String getSignUpForm() {
		return "user/form";
	}

	@GetMapping("/users/check/{userID}")
	public String checkUserID(@PathVariable String userID, HttpSession session, Model model) {
		userService.checkUserID((UserRequest)session.getAttribute("sessionUser"), userID);
		model.addAttribute("userID", userID);
		return "user/checkForUpdate";
	}

	@GetMapping("/users")
	public String getUserList(Model model) {
		List<UserResponse> userResponses = userService.findUsers();
		model.addAttribute("users", userResponses);
		return "user/list";
	}

	@GetMapping("/users/{userID}")
	public String getProfileByUserID(@PathVariable String userID, Model model) {
		UserResponse userResponse = userService.findByUserID(userID);
		model.addAttribute("user", userResponse);
		return "user/profile";
	}

	@GetMapping("/users/update-form")
	public String updateForm(HttpSession session, Model model) {
		UserRequest userRequest = (UserRequest)session.getAttribute("sessionUser");
		model.addAttribute("user", userRequest);
		if (session.getAttribute("passwordCheck") == null) {
			throw new InvalidAccessException();
		}
		session.removeAttribute("passwordCheck");
		return "user/updateForm";
	}
}
