package kr.codesqaud.cafe.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.domain.user.dto.UserSaveRequestDto;
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
}
