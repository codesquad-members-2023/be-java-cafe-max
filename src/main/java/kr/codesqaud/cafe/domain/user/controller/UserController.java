package kr.codesqaud.cafe.domain.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import kr.codesqaud.cafe.domain.user.dto.request.UserSaveRequestDto;
import kr.codesqaud.cafe.domain.user.dto.request.UserUpdateRequestDto;
import kr.codesqaud.cafe.domain.user.dto.response.UserDetailResponseDto;
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
		List<UserDetailResponseDto> userDetailResponseDtos = new ArrayList<>();
		users.forEach(user -> userDetailResponseDtos.add(new UserDetailResponseDto(user)));
		model.addAttribute("users", userDetailResponseDtos);
		return "user/list";
	}

	@GetMapping("/users/{id}")
	public String findUserProfile(@PathVariable("id") Long id, Model model) {
		User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
		model.addAttribute("user", new UserDetailResponseDto(user));
		return "/user/profile";
	}

	@GetMapping("/users/{id}/form")
	public String updateUserProfile(@PathVariable("id") Long id, Model model) {
		User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
		model.addAttribute("user", new UserDetailResponseDto(user));
		return "/user/updateForm";
	}

	@PutMapping("/users/{id}/update")
	public String modifyUserProfile(UserUpdateRequestDto userUpdateDto, @PathVariable("id") Long id) {
		userRepository.update(userUpdateDto.toEntity(id));
		return "redirect:/users";
	}
}
