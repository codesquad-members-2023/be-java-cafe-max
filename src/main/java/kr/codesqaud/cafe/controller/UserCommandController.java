package kr.codesqaud.cafe.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;
import kr.codesqaud.cafe.service.UserService;

@Controller
public class UserCommandController {
	private final UserService userService;

	public UserCommandController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user/create")
	public String createUser(UserDto userDto) {
		userService.create(userDto);
		return "redirect:/users";
	}

	@PatchMapping("/user/{userID}")
	public String updateUser(UserDto userDto) {
		userService.update(userDto);
		return "redirect:/users";
	}

	@PostMapping("/login")
	public String login(String userID, String password, HttpSession session) {
		User user = userService.findOne(userID);
		if(user == null) {
			return "redirect:/user/login";
		}
		if(!Objects.equals(user.getPassword(), password)) {
			return "redirect:/user/login";
		}
		session.setAttribute("sessionUser", user);
		return "redirect:/";
	}
}
