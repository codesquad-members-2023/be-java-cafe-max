package kr.codesqaud.cafe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public void userSignUp(SignUpRequest signUpRequest) {
		User user = new User(
			signUpRequest.getUserId(), signUpRequest.getPassword(),
			signUpRequest.getName(), signUpRequest.getEmail());
		userRepository.save(user);
	}

	@Transactional
	public List<UserDto> findAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			userDtoList.add(new UserDto(i + 1L, user.getUserId(), user.getName(), user.getEmail()));
		}
		return userDtoList;
	}

	@Transactional
	public User userProfile(String id) {
		User user = userRepository.findUserProfile(id).orElseThrow(() ->
			new IllegalArgumentException("회원을 찾을 수 없습니다"));
		return user;
	}
}
