package kr.codesqaud.cafe.domain.user.controller;

import org.springframework.stereotype.Controller;

import kr.codesqaud.cafe.domain.user.repository.UserRepository;

@Controller
public class UserController {

	private final UserRepository userRepository;

	UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
