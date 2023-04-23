package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.common.consts.SessionConst.SESSION_USER;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.codesqaud.cafe.controller.dto.req.JoinRequest;
import kr.codesqaud.cafe.controller.dto.req.ProfileEditRequest;
import kr.codesqaud.cafe.exception.DuplicatedUserIdException;
import kr.codesqaud.cafe.exception.InvalidPasswordException;
import kr.codesqaud.cafe.service.UserService;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user/create")
	public String join(@ModelAttribute final JoinRequest request) {
		userService.join(request);
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String showAllUsers(final Model model) {
		model.addAttribute("users", userService.getUsers());
		return "user/list";
	}

	@GetMapping("/users/{userId}")
	public String showProfilePage(@PathVariable final String userId, final Model model) {
		model.addAttribute("user", userService.findByUserId(userId));
		return "user/profile";
	}

	@GetMapping("/users/{userId}/form")
	public String showProfileEditPage(@PathVariable final String userId,
	                                  @SessionAttribute(SESSION_USER) final String sessionUserId,
	                                  final Model model) {
		userService.validateHasAuthorization(sessionUserId, userId);
		model.addAttribute("userId", userId);
		return "user/edit_form";
	}

	@PutMapping("/users/{userId}")
	public String editUserProfile(@PathVariable final String userId,
	                              @SessionAttribute(SESSION_USER) final String sessionUserId,
	                              @ModelAttribute final ProfileEditRequest request) {
		userService.validateHasAuthorization(sessionUserId, userId);
		userService.editUserProfile(userId, request);
		return "redirect:/users";
	}

	@ExceptionHandler(DuplicatedUserIdException.class)
	public String handleDuplicatedUserId(final DuplicatedUserIdException e, final Model model) {
		model.addAttribute("error", e.getMessage());
		return "user/form";
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public String handleInvalidPassword(final InvalidPasswordException e, final Model model) {
		model.addAttribute("error", e.getMessage());
		return "user/edit_form";
	}
}
