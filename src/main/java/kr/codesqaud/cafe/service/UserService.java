package kr.codesqaud.cafe.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;
import kr.codesqaud.cafe.exception.DuplicateUserException;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import kr.codesqaud.cafe.repository.user.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean create(UserDto userDto) {
		validateDuplicate(userDto);
		User user = new User(userDto.getUserID(), userDto.getEmail(), userDto.getNickname(),
			userDto.getPassword(), LocalDate.now());
		userRepository.create(user);
		return true;
	}

	private boolean validateDuplicate(UserDto userDto) {
		if (userRepository.existUserID(userDto.getUserID())) {
			throw new DuplicateUserException("아이디");
		}
		if (userRepository.existEmail(userDto.getEmail())) {
			throw new DuplicateUserException("이메일");
		}
		if (userRepository.existNickname(userDto.getNickname())) {
			throw new DuplicateUserException("닉네임");
		}
		return true;
	}

	public boolean update(UserDto userDto) {
		validateUpdateDuplicate(userDto);
		userRepository.update(userDto);
		return true;
	}

	private void validateUpdateDuplicate(UserDto userDto) {
		if (userRepository.existUpdateEmail(userDto.getUserID(), userDto.getEmail())) {
			throw new DuplicateUserException("이메일");
		}
		if (userRepository.existUpdateNickname(userDto.getUserID(), userDto.getNickname())) {
			throw new DuplicateUserException("닉네임");
		}
	}

	public List<User> findUsers() {
		return userRepository.findAll();
	}

	public User findOne(String userID) {
		return userRepository.findByUserID(userID)
			.orElseThrow(() -> new UserNotFoundException("유저 아이디를 찾을 수 없습니다."));
	}
}
