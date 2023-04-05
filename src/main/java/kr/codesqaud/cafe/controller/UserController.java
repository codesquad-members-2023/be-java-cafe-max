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

	@GetMapping("/users/list")
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

	@GetMapping("/users")
	public String list(Model model) {
		List<User> users = userService.findUsers();
		model.addAttribute("users", users);
		return "/user/list";
	}

	@GetMapping("/users/{userID}")
	public String profile(@PathVariable("userID") String userID, Model model) {
		model.addAttribute("user", userService.findOne(userID).get());
		return "/user/profile";
	}

	@GetMapping("/users/{userID}/form")
	public String updateForm(@PathVariable String userID, Model model) {
		model.addAttribute("user", userService.findOne(userID).get());
		return "/user/updateForm";
	}

	@PutMapping("/user/{index}/{signUpDate}/update")
	public String updateUserInfo(@PathVariable long index, @PathVariable String signUpDate, UserDto userDto) {
		userService.update(index, signUpDate, userDto);
		return "redirect:/users";
	}
}
