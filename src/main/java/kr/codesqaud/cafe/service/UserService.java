package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.controller.dto.UserDto;
import kr.codesqaud.cafe.controller.dto.req.JoinRequest;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.exception.DuplicatedUserIdException;
import kr.codesqaud.cafe.exception.NotFoundException;
import kr.codesqaud.cafe.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void join(final JoinRequest request) {
		User user = User.from(request);
		userRepository.save(user)
			.orElseThrow(() -> new DuplicatedUserIdException("해당 아이디는 이미 존재합니다."));
	}

	public List<UserDto> getUsers() {
		List<User> users = userRepository.findAll();
		return IntStream.rangeClosed(1, users.size())
			.mapToObj(index -> UserDto.from(users.get(index - 1), index))
			.collect(Collectors.toUnmodifiableList());
	}

	public UserDto findByUserId(final String userId) {
		return userRepository.findByUserId(userId)
			.map(UserDto::from)
			.orElseThrow(() -> new NotFoundException(userId.concat("는 존재하지 않는 아이디입니다.")));
	}
}
