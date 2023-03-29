package kr.codesqaud.cafe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.controller.dto.SignUpRequest;
import kr.codesqaud.cafe.controller.dto.UserDto;
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

	public List<UserDto> findAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			userDtos.add(new UserDto(i + 1L, user.getUserId(), user.getName(), user.getEmail()));
		}
		return userDtos;
	}
}
