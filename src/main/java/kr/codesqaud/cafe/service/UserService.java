package kr.codesqaud.cafe.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;
import kr.codesqaud.cafe.exception.DuplicateUserException;
import kr.codesqaud.cafe.repository.UserRepository;

public class UserService {
	private final UserRepository userRepository;
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
	private long index = 0L;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public long join(UserDto userDto) {
		validateDuplicate(userDto);
		User user = new User(increaseIndex(), userDto.getUserID(), userDto.getEmail(), userDto.getNickname(),
			userDto.getPassword(), signUpDate());
		userRepository.save(user);
		return user.getIndex();
	}

	private long increaseIndex() {
		return ++index;
	}

	private void validateDuplicate(UserDto userDto) {
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
	}

	private String signUpDate() {
		LocalDate now = LocalDate.now();
		return now.format(DATE_FORMATTER);
	}

	public boolean update(long index, String signUpDate, UserDto userDto) {
		User newUser = new User(index, userDto.getUserID()
			, userDto.getEmail(), userDto.getNickname(), userDto.getPassword(), signUpDate);
		long userIndex = newUser.getIndex() - 1;

		userRepository.update(userIndex, newUser);
		return true;
	}

	public List<User> findUsers() {
		return userRepository.findAll();
	}

	public Optional<User> findOne(String userID) {
		return userRepository.findByUserID(userID);
	}
}
