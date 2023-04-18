package kr.codesqaud.cafe.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
	public String createUser(@Valid UserDto userDto) {
		userService.create(userDto);
		return "redirect:/users";
	}

	@PatchMapping("/user/update")
	public String updateUser(@Valid UserDto userDto, HttpSession httpSession) {
		String original = (userService.findOne(userDto.getUserID())).getNickname();
		userService.update(userDto);
		User user = userService.findOne(userDto.getUserID());
		httpSession.setAttribute("sessionUser", user);
		if (!original.equals(user.getNickname())) {
			return "redirect:/article/update/" + original + "/" + user.getNickname();
		}
		return "redirect:/users";
	}

	@PostMapping("/login")
	public String login(String userID, String password, HttpSession session) {
		User user = userService.findOne(userID);
		user.validatePassword(password);
		if (!Objects.equals(user.getPassword(), password)) {
			return "redirect:/user/login";
		}
		session.setAttribute("sessionUser", user);
		return "redirect:/";
	}

	@PostMapping("/user/form")
	public String getUpdateForm(String password, HttpSession session) {
		User user = (User)session.getAttribute("sessionUser");
		if (!password.equals(user.getPassword())) {
			return "redirect:/checkForUpdate";
		}
		return "redirect:/user/updateForm";
	}
}
