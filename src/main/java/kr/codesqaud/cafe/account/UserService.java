package kr.codesqaud.cafe.account;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.account.dto.ProfileEditRequest;
import kr.codesqaud.cafe.account.dto.SignInRequest;
import kr.codesqaud.cafe.account.dto.UserListResponse;
import kr.codesqaud.cafe.account.dto.UserResponse;
import kr.codesqaud.cafe.account.dto.UserSignUpRequest;
import kr.codesqaud.cafe.account.exception.AlreadyUserExistenceException;
import kr.codesqaud.cafe.account.exception.LoginInvalidPasswordException;
import kr.codesqaud.cafe.account.exception.UserNotFoundException;
import kr.codesqaud.cafe.account.exception.UserUpdateInvalidPasswordException;
import kr.codesqaud.cafe.account.repository.UserRepository;
import kr.codesqaud.cafe.global.mapper.UserMapper;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public UserService(@Qualifier("jdbcRepository") UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public void save(UserSignUpRequest userSignUpRequest) {
		validateId(userSignUpRequest.getId());
		userRepository.save(userMapper.toUser(userSignUpRequest));
	}

	private void validateId(String id) {
		if (userRepository.exist(id)) {
			throw new AlreadyUserExistenceException();
		}
	}

	public List<UserListResponse> getUsers() {
		return userRepository.findAll().stream()
			.map(userMapper::toUserListResponse)
			.collect(Collectors.toUnmodifiableList());
	}

	public UserResponse getUserById(String id) {
		return userRepository.findUserById(id)
			.map(userMapper::toUserResponse)
			.orElseThrow(UserNotFoundException::new);
	}

	public void update(ProfileEditRequest profileEditRequest) {
		UserResponse userResponse = getUserById(profileEditRequest.getId());

		if (!matchPassword(profileEditRequest, userResponse)) {
			throw new UserUpdateInvalidPasswordException();
		}

		userRepository.updateUser(userMapper.toUser(profileEditRequest));
	}

	private boolean matchPassword(ProfileEditRequest profileEditRequest, UserResponse userResponse) {
		return profileEditRequest.isMatchWithResponsePassword(userResponse.getPassword());
	}

	public void matchPassword(SignInRequest signInRequest) {
		UserResponse userResponse = getUserById(signInRequest.getId());
		if (!signInRequest.isMatchWithResponsePassword(userResponse.getPassword())) {
			throw new LoginInvalidPasswordException();
		}
	}

}
