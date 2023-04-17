package kr.codesqaud.cafe.service;

import static kr.codesqaud.cafe.fixture.FixtureFactory.createUser;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesqaud.cafe.controller.dto.req.JoinRequest;
import kr.codesqaud.cafe.controller.dto.req.ProfileEditRequest;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.exception.DuplicatedUserIdException;
import kr.codesqaud.cafe.exception.InvalidPasswordException;
import kr.codesqaud.cafe.exception.NotFoundException;
import kr.codesqaud.cafe.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@DisplayName("회원가입을 수행할 때 ")
	@Nested
	class JoinTest {

		@DisplayName("올바른 회원가입 요청이 들어오면 회원가입에 성공한다.")
		@Test
		void givenJoinRequest_whenJoin_thenReturnsNothing() {
			// given
			given(userRepository.save(any(User.class))).willReturn(Optional.of(createUser()));
			JoinRequest joinRequest = new JoinRequest("userId", "password", "name", "email@email.com");

			// when & then
			assertAll(
				() -> assertThatCode(() -> userService.join(joinRequest))
					.doesNotThrowAnyException(),
				() -> then(userRepository).should().save(any(User.class))
			);
		}

		@DisplayName("중복된 로그인 아이디를 가진 요청이 들어오면 예외를 던진다.")
		@Test
		void givenDuplicatedLoginIdJoinRequest_whenJoin_thenThrowsException() {
			// given
			given(userRepository.save(any(User.class))).willReturn(Optional.empty());
			JoinRequest joinRequest = new JoinRequest("duplicatedUserId", "password", "name", "email@email.com");

			// when & then
			assertAll(
				() -> assertThatThrownBy(() -> userService.join(joinRequest))
					.isInstanceOf(DuplicatedUserIdException.class),
				() -> then(userRepository).should().save(any(User.class))
			);
		}
	}

	@DisplayName("회원 아이디로 회원을 조회할 때")
	@Nested
	class UserFindTest {

		@DisplayName("해당 아이디를 가진 회원이 있으면 회원 정보를 반환한다.")
		@Test
		void givenUserId_whenFindByUserId_thenReturnsUser() {
			// given
			given(userRepository.findByUserId(anyString())).willReturn(Optional.of(createUser()));

			// when & then
			assertAll(
				() -> assertThatCode(() -> userService.findByUserId("bruni"))
					.doesNotThrowAnyException(),
				() -> then(userRepository).should().findByUserId("bruni")
			);
		}

		@DisplayName("해당 아이디를 가진 회원이 없으면 예외를 던진다.")
		@Test
		void givenNotExistsUserId_whenFindByUserId_thenThrowsException() {
			// given
			given(userRepository.findByUserId(anyString())).willReturn(Optional.empty());

			// when & then
			assertAll(
				() -> assertThatThrownBy(() -> userService.findByUserId("bruni"))
					.isInstanceOf(NotFoundException.class),
				() -> then(userRepository).should().findByUserId("bruni")
			);
		}
	}

	@DisplayName("회원정보를 수정할 때")
	@Nested
	class ProfileEditTest {

		@DisplayName("수정 정보가 주어지면 회원정보 수정에 성공한다.")
		@Test
		void givenProfileEditRequest_whenEditProfile_thenReturnsNothing() {
			// given
			given(userRepository.findByUserId(anyString())).willReturn(Optional.of(createUser()));
			willDoNothing().given(userRepository).update(any(User.class));
			ProfileEditRequest request = new ProfileEditRequest("qwer1234",
																"newPassword",
																"브으루우니이",
																"bbruunii@codeSquad.com");

			// when & then
			assertAll(
				() -> assertThatCode(() -> userService.editUserProfile("bruni", request))
					.doesNotThrowAnyException(),
				() -> then(userRepository).should().findByUserId("bruni"),
				() -> then(userRepository).should().update(any(User.class))
			);
		}

		@DisplayName("존재하지 않는 유저 아이디가 주어지면 예외를 던진다.")
		@Test
		void givenNotExistsUserId_whenEditProfile_thenThrowsException() {
			// given
			given(userRepository.findByUserId(anyString())).willReturn(Optional.empty());

			ProfileEditRequest request = new ProfileEditRequest("qwer1234",
																"newPassword",
																"브으루우니이",
																"bbruunii@codeSquad.com");

			// when & then
			assertAll(
				() -> assertThatThrownBy(() -> userService.editUserProfile("notExists", request))
					.isInstanceOf(NotFoundException.class),
				() -> then(userRepository).should().findByUserId("notExists"),
				() -> then(userRepository).should(never()).update(any(User.class))
			);
		}

		@DisplayName("기존 비밀번호와 일치하지 않는 수정 정보가 주어지면 예외를 던진다.")
		@Test
		void givenWrongOriPassword_whenEditProfile_thenThrowsException() {
			// given
			given(userRepository.findByUserId(anyString())).willReturn(Optional.of(createUser()));
			ProfileEditRequest request = new ProfileEditRequest("wrong",
																"newPassword",
																"브으루우니이",
																"bbruunii@codeSquad.com");

			// when & then
			assertAll(
				() -> assertThatThrownBy(() -> userService.editUserProfile("bruni", request))
					.isInstanceOf(InvalidPasswordException.class),
				() -> then(userRepository).should().findByUserId("bruni"),
				() -> then(userRepository).should(never()).update(any(User.class))
			);
		}
	}
}
