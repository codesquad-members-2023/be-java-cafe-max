package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.dto.UserRequest;
import kr.codesqaud.cafe.dto.ValidationGroups;
import kr.codesqaud.cafe.service.UserService;

@Controller
public class UserCommandController {
	private final UserService userService;

	public UserCommandController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users")
	public String createUser(@Validated(ValidationGroups.userCreateValidationGroup.class) UserRequest userRequest) {
		userService.create(userRequest);
		return "redirect:/users";
	}

	@PatchMapping("/users")
	public String updateUser(@Validated(ValidationGroups.userUpdateValidationGroup.class) UserRequest userRequest,
		HttpSession httpSession) {
		userService.update(userRequest);
		httpSession.setAttribute("sessionUser", userRequest);
		return "redirect:/users";
	}

	@PostMapping("/users/{userID}")
	public String getUpdateForm(String password, HttpSession session) {
		userService.checkPassword((UserRequest)session.getAttribute("sessionUser"), password);
		session.setAttribute("passwordCheck", true);
		return "redirect:/users/update-form";
	}
}
