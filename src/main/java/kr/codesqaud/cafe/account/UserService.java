package kr.codesqaud.cafe.account;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.account.dto.ProfileEditDTO;
import kr.codesqaud.cafe.account.dto.SignInRequest;
import kr.codesqaud.cafe.account.dto.UserDTO;
import kr.codesqaud.cafe.account.dto.UserListDTO;
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

	public UserService(@Qualifier("jdbcRepository") UserRepository userRepository) {
		this.userRepository = userRepository;
		this.userMapper = new UserMapper();
	}

	public void addUser(UserDTO userDTO) {
		validateId(userDTO.getId());
		userRepository.save(userMapper.toUser(userDTO));
	}

	private void validateId(String id) {
		if (userRepository.exist(id)) {
			throw new AlreadyUserExistenceException();
		}
	}

	public List<UserListDTO> getUserList() {
		return userRepository.findAll().stream()
			.map(userMapper::toUserListDTO)
			.collect(Collectors.toUnmodifiableList());
	}

	public UserDTO getUserById(String id) {
		return userRepository.findUserById(id)
			.map(userMapper::toUserDTO)
			.orElseThrow(UserNotFoundException::new);
	}

	public void updateUser(ProfileEditDTO profileEditDto) {
		UserDTO userDto = getUserById(profileEditDto.getId());

		if (!matchPassword(profileEditDto, userDto)) {
			throw new UserUpdateInvalidPasswordException();
		}

		userRepository.updateUser(userMapper.toUser(profileEditDto));
	}

	private boolean matchPassword(ProfileEditDTO profileEditDto, UserDTO userDto) {
		return userDto.getPassword().equals(profileEditDto.getOriPassword());
	}

	public void matchPassword(SignInRequest signInRequest) {
		UserDTO userdto = getUserById(signInRequest.getId());
		if (!signInRequest.getPassword().equals(userdto.getPassword())) {
			throw new LoginInvalidPasswordException();
		}
	}

}
