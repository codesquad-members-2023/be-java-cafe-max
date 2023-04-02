package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.controller.dto.UserDto;
import kr.codesqaud.cafe.controller.dto.req.JoinRequest;
import kr.codesqaud.cafe.controller.dto.req.ProfileEditRequest;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.exception.DuplicatedUserIdException;
import kr.codesqaud.cafe.exception.InvalidPasswordException;
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
			.orElseThrow(DuplicatedUserIdException::new);
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

	public void editUserProfile(final String userId, final ProfileEditRequest request) {
		User savedUser = userRepository.findByUserId(userId)
			.orElseThrow(() -> new NotFoundException(userId.concat("를 가진 회원을 찾을 수 없습니다.")));

		validatePassword(savedUser, request.getOriginalPassword());
		savedUser.editProfile(request.getNewPassword(), request.getName(), request.getEmail());
	}

	private void validatePassword(final User user, final String password) {
		if (!user.isSamePassword(password)) {
			throw new InvalidPasswordException();
		}
	}
}
