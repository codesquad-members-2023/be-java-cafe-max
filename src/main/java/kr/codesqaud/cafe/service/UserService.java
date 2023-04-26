package kr.codesqaud.cafe.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserRequest;
import kr.codesqaud.cafe.dto.UserResponse;
import kr.codesqaud.cafe.exception.DuplicateUserException;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import kr.codesqaud.cafe.repository.user.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean create(UserRequest userRequest) {
		validateDuplicate(userRequest);
		User user = new User(userRequest.getUserID(), userRequest.getEmail(), userRequest.getNickname(),
			userRequest.getPassword(), LocalDate.now());
		userRepository.create(user);
		return true;
	}

	private boolean validateDuplicate(UserRequest userRequest) {
		if (userRepository.existUserID(userRequest.getUserID())) {
			throw new DuplicateUserException("아이디");
		}
		validateUpdateDuplicate(userRequest);
		return true;
	}

	private void validateUpdateDuplicate(UserRequest userRequest) {
		if (userRepository.existUpdateEmail(userRequest.getUserID(), userRequest.getEmail())) {
			throw new DuplicateUserException("이메일");
		}
		if (userRepository.existUpdateNickname(userRequest.getUserID(), userRequest.getNickname())) {
			throw new DuplicateUserException("닉네임");
		}
	}

	public boolean update(UserRequest userRequest) {
		validateUpdateDuplicate(userRequest);
		User user = new User(userRequest.getUserID(), userRequest.getEmail(), userRequest.getNickname(),
			userRequest.getPassword());
		userRepository.update(user);
		return true;
	}

	public List<UserResponse> findUsers() {
		return userRepository.findAll().stream().map(user -> new UserResponse(user.getUserIndex(), user.getUserID(),
				user.getEmail(), user.getNickname(), user.getPassword(), user.getSignUpDate()))
			.collect(Collectors.toList());
	}

	public UserRequest checkLogin(String userID, String password) {
		User user = userRepository.findByUserID(userID)
			.orElseThrow(UserNotFoundException::new);
		user.validatePassword(password);
		return new UserRequest(user.getUserID(), user.getEmail(), user.getNickname(), user.getPassword());
	}

	public void checkPassword(UserRequest userRequest, String password) {
		User user = new User(userRequest.getUserID(), userRequest.getEmail(), userRequest.getNickname(),
			userRequest.getPassword());
		user.validatePassword(password);
	}

	public void checkUserID(UserRequest userRequest, String userID) {
		User user = new User(userRequest.getUserID(), userRequest.getEmail(), userRequest.getNickname(),
			userRequest.getPassword());
		user.validateUserId(userID);
	}

	public UserResponse findByUserID(String userID) {
		User user = userRepository.findByUserID(userID)
			.orElseThrow(UserNotFoundException::new);
		return new UserResponse(user.getUserIndex(), user.getUserID(), user.getEmail(), user.getNickname(),
			user.getPassword(), user.getSignUpDate());
	}
}
