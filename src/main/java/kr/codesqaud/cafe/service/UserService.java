package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.controller.dto.SignUpRequest;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void signUp(SignUpRequest signUpRequest) {
		User user = new User(
			signUpRequest.getUserId(), signUpRequest.getPassword(),
			signUpRequest.getName(), signUpRequest.getEmail());
		userRepository.save(user);
	}
}
