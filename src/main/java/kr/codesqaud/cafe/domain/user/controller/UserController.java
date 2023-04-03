package kr.codesqaud.cafe.domain.user.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import kr.codesqaud.cafe.domain.user.dto.UserSaveRequestDto;
import kr.codesqaud.cafe.domain.user.dto.UserUpdateDto;
import kr.codesqaud.cafe.domain.user.entity.User;
import kr.codesqaud.cafe.domain.user.repository.UserRepository;

@Controller
public class UserController {

	private final UserRepository userRepository;

	UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping("/user/join")
	public String signIn(UserSaveRequestDto userSaveRequestDto) {
		userRepository.save(userSaveRequestDto.toEntity());
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String getUsers(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "user/list";
	}

	@GetMapping("/users/{id}")
	public String findUserProfile(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", userRepository.findById(id));
		return "/user/profile";
	}

	@GetMapping("/users/{id}/form")
	public String updateUserProfile(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", userRepository.findById(id));
		return "/user/updateForm";
	}

	@PutMapping("/users/{id}/update")
	public String modifyUserProfile(UserUpdateDto userUpdateDto, @PathVariable("id") Long id) {
		userRepository.update(userUpdateDto.toEntity(id));
		return "redirect:/users";
	}
}
