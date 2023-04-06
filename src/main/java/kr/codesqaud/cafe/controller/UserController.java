package kr.codesqaud.cafe.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;
import kr.codesqaud.cafe.service.UserService;

@Controller
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public String userList(Model model) {
		List<User> users = userService.findUsers();
		model.addAttribute("users", users);
		return "/user/list";
	}

	@PostMapping("/user/create")
	public String create(UserDto userDto) {
		userService.join(userDto);
		return "redirect:/users";
	}

	@GetMapping("/users/{userID}")
	public String profile(@PathVariable String userID, Model model) {
		model.addAttribute("user", userService.findOne(userID).get());
		return "/user/profile";
	}

	@GetMapping("/users/{userID}/form")
	public String updateForm(@PathVariable String userID, Model model) {
		model.addAttribute("user", userService.findOne(userID).get());
		return "/user/updateForm";
	}

	@PutMapping("/user/{userID}/update")
	public String updateUserInfo(UserDto userDto) {
		userService.update(userDto);
		return "redirect:/users";
	}
}
