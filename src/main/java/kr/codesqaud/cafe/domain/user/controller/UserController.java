package kr.codesqaud.cafe.domain.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
import kr.codesqaud.cafe.global.exception.user.DuplicatedUserIdException;
import kr.codesqaud.cafe.global.exception.user.InvalidPasswordException;

@Controller
public class UserController {

	private final UserRepository userRepository;

	UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping("/user/join")
	public String signIn(UserSaveRequestDto userSaveRequestDto) {
		if (userRepository.findById(userSaveRequestDto.getUsername()).isPresent()) {
			throw new DuplicatedUserIdException();
		}
		userRepository.save(userSaveRequestDto.toEntity());
		return "redirect:/user/login";
	}

	@GetMapping("/users")
	public String getUsers(Model model) {
		List<User> users = userRepository.findAll();
		List<UserDetailResponseDto> userDetailResponseDtos = new ArrayList<>();
		users.forEach(user -> userDetailResponseDtos.add(new UserDetailResponseDto(user)));
		model.addAttribute("users", userDetailResponseDtos);
		return "user/list";
	}

	@GetMapping("/users/{username}")
	public String findUserProfile(@PathVariable("username") String username, Model model) {

		Optional<User> user = userRepository.findById(username);
		if (user.isEmpty()) {
			return "redirect:/";
		}
		model.addAttribute("user", new UserDetailResponseDto(user.get()));
		return "/user/profile";
	}

	@GetMapping("/users/{loginUserId}/form")
	public String updateUserProfile(@PathVariable("loginUserId") String username, Model model) {
		User user = userRepository.findById(username).orElseThrow(NoSuchElementException::new);
		model.addAttribute("user", new UserDetailResponseDto(user));
		return "/user/updateForm";
	}

	@PutMapping("/users/{loginUserId}/update")
	public String modifyUserProfile(UserUpdateRequestDto userUpdateDto, @PathVariable("loginUserId") String username) {
		if (!userRepository.findById(username).get().matchPassword(userUpdateDto.getPassword())) {
			throw new InvalidPasswordException();
		}
		userRepository.update(userUpdateDto.toEntity(username));
		return "redirect:/users";
	}
}
