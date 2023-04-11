package kr.codesqaud.cafe.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;
import kr.codesqaud.cafe.exception.DuplicateUserException;
import kr.codesqaud.cafe.repository.UserRepository;

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
		userRepository.findByUserID(userDto.getUserID())
			.ifPresent(u -> {
				throw new DuplicateUserException("이미 존재하는 회원입니다.");
			});
		userRepository.findByEmail(userDto.getEmail())
			.ifPresent(u -> {
				throw new DuplicateUserException("이미 존재하는 회원 이메일입니다.");
			});
		userRepository.findByNickname(userDto.getNickname())
			.ifPresent(u -> {
				throw new DuplicateUserException("이미 존재하는 회원 닉네임입니다.");
			});
		return true;
	}

	public boolean update(UserDto userDto) {
		userRepository.update(userDto);
		return true;
	}

	public List<User> findUsers() {
		return userRepository.findAll();
	}

	public User findOne(String userID) {
		return userRepository.findByUserID(userID).get();
	}
}
