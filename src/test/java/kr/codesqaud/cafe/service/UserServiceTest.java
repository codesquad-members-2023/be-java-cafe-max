package kr.codesqaud.cafe.service;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesqaud.cafe.account.UserService;
import kr.codesqaud.cafe.account.domain.User;
import kr.codesqaud.cafe.account.dto.ProfileEditRequest;
import kr.codesqaud.cafe.account.dto.SignInRequest;
import kr.codesqaud.cafe.account.dto.UserResponse;
import kr.codesqaud.cafe.account.dto.UserResponseForList;
import kr.codesqaud.cafe.account.dto.UserSignUpRequest;
import kr.codesqaud.cafe.account.exception.IdDuplicatedException;
import kr.codesqaud.cafe.account.exception.LoginInvalidPasswordException;
import kr.codesqaud.cafe.account.exception.UserNotFoundException;
import kr.codesqaud.cafe.account.exception.UserUpdateInvalidPasswordException;
import kr.codesqaud.cafe.account.repository.UserRepository;
import kr.codesqaud.cafe.global.mapper.UserMapper;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepository;

	@Mock
	UserMapper userMapper;

	private User user;

	private UserSignUpRequest userSignUpRequest;

	private UserResponse userResponse;

	private UserResponseForList userResponseForList;

	private static final String nickName = "nickName";

	private static final String email = "test@Email.com";

	private static final String password = "password";

	private static final String userId = "tester";

	private static final String date = "2023-4-27";

	@BeforeEach
	void setUp() {
		user = new User(nickName, email, password, userId);
		userSignUpRequest = new UserSignUpRequest(nickName, email, password, userId);
		userResponse = new UserResponse(nickName, email, password, userId);
		userResponseForList = new UserResponseForList(nickName, email, userId, date);
	}

	@Test
	@DisplayName("중복되지 않은 아이디는 회원가입할수있다.")
	void addUser() {
		//given
		given(userRepository.exist(userId)).willReturn(false);
		given(userMapper.toUser(userSignUpRequest)).willReturn(user);

		//when
		userService.addUser(userSignUpRequest);

		//then
		verify(userRepository, times(1)).save(user);
	}

	@Test
	@DisplayName("중복된 아이디는 회원가입할수없다.")
	void addUser_throwsException() {
		//given
		given(userRepository.exist(userId)).willReturn(true);

		//when
		Assertions.assertThatThrownBy(() -> userService.addUser(userSignUpRequest))
			.isInstanceOf(IdDuplicatedException.class);

		//then
		verify(userRepository, times(0)).save(user);
	}

	@Test
	@DisplayName("getUserList 메서드를 통해 사용자 리스트를 가져올수있다.")
	void getUserList() {
		//given
		List<User> users = new ArrayList<>(Arrays.asList(user));
		given(userRepository.findAll()).willReturn(users);
		given(userMapper.toUserListResponse(user)).willReturn(userResponseForList);

		//when
		List<UserResponseForList> result = userService.getUserList();

		//then
		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(result.get(0)).isEqualTo(userResponseForList);
			softly.assertThat(result.size()).isEqualTo(1);
		});
	}

	@Test
	@DisplayName("아이디가 userId인 사용자에 대한 정보를 가져올수있다.")
	void getUserById() {
		//given
		given(userRepository.findUserById(userId)).willReturn(Optional.of(user));
		given(userMapper.toUserResponse(user)).willReturn(userResponse);

		//when
		UserResponse result = userService.getUserById(userId);

		//then
		Assertions.assertThat(result.getUserId()).isEqualTo(userId);
	}

	@Test
	@DisplayName("아이디가 userId인 사용자에 대한 정보가 존재하지 않으면 UserNotFoundException이 발생한다.")
	void getUserById_throwsException() {
		//given
		given(userRepository.findUserById(userId)).willReturn(Optional.empty());

		//when
		Assertions.assertThatThrownBy(() -> userService.getUserById(userId))
			.isInstanceOf(UserNotFoundException.class);
	}

	@Test
	@DisplayName("개인정보를 수정할때 비밀번호가 일치하면 정보수정에 성공한다.")
	void updateUser() {
		//given
		ProfileEditRequest profileEditRequest = new ProfileEditRequest(nickName, email, password, password, userId);
		User newUser = new User(nickName, nickName, password, userId);
		given(userRepository.findUserById(userId)).willReturn(Optional.of(user));
		given(userMapper.toUserResponse(user)).willReturn(userResponse);
		given(userMapper.toUser(profileEditRequest)).willReturn(newUser);

		//when & then
		Assertions.assertThatCode(() -> userService.updateUser(profileEditRequest)).doesNotThrowAnyException();
		verify(userRepository, times(1)).updateUser(newUser);
	}

	@Test
	@DisplayName("개인정보를 수정할때 비밀번호가 일치하지 않으면 UserUpdateInvalidPasswordException이 발생한다.")
	void updateUser_throwsException() {
		//given
		ProfileEditRequest profileEditRequest = new ProfileEditRequest(nickName, email, password,
			password + "different", userId);
		given(userRepository.findUserById(userId)).willReturn(Optional.of(user));
		given(userMapper.toUserResponse(user)).willReturn(userResponse);

		//when & then
		Assertions.assertThatThrownBy(() -> userService.updateUser(profileEditRequest)).isInstanceOf(
			UserUpdateInvalidPasswordException.class);

	}

	@Test
	@DisplayName("로그인시 비밀번호를 정확히 입력해야 로그인에 성공한다.")
	void matchPassword() {
		//given
		SignInRequest signInRequest = new SignInRequest(userId, password);
		given(userRepository.findUserById(userId)).willReturn(Optional.of(user));
		given(userMapper.toUserResponse(user)).willReturn(userResponse);

		//when & then
		Assertions.assertThatCode(() -> userService.matchPassword(signInRequest)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("로그인시 비밀번호를 정확히 입력하지 않으면 LoginInvalidPasswordException이 발생한다.")
	void matchPassword_throwsException() {
		//given
		SignInRequest signInRequest = new SignInRequest(userId, password + "different");
		given(userRepository.findUserById(userId)).willReturn(Optional.of(user));
		given(userMapper.toUserResponse(user)).willReturn(userResponse);

		//when & then
		Assertions.assertThatThrownBy(() -> userService.matchPassword(signInRequest)).isInstanceOf(
			LoginInvalidPasswordException.class);
	}
}