package kr.codesqaud.cafe.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kr.codesqaud.cafe.controller.dto.req.JoinRequest;
import kr.codesqaud.cafe.exception.DuplicatedUserIdException;
import kr.codesqaud.cafe.exception.NotFoundException;
import kr.codesqaud.cafe.repository.impl.UserMemoryRepository;

class UserServiceTest {

	private UserService userService;

	@BeforeEach
	void setUserRepository() {
		this.userService = new UserService(new UserMemoryRepository());
	}

	@DisplayName("회원가입을 수행할 때 ")
	@Nested
	class JoinTest {

		@DisplayName("올바른 회원가입 요청이 들어오면 회원가입에 성공한다.")
		@Test
		void givenJoinRequest_whenJoin_thenReturnsNothing() {
			// given
			JoinRequest joinRequest = new JoinRequest("userId", "password", "name", "email@email.com");

			// when & then
			assertThatCode(() -> userService.join(joinRequest))
				.doesNotThrowAnyException();
		}

		@DisplayName("중복된 로그인 아이디를 가진 요청이 들어오면 예외를 던진다.")
		@Test
		void givenDuplicatedLoginIdJoinRequest_whenJoin_thenThrowsException() {
			// given
			JoinRequest joinRequest = new JoinRequest("duplicatedUserId", "password", "name", "email@email.com");
			userService.join(joinRequest);

			// when & then
			assertThatThrownBy(() -> userService.join(joinRequest))
				.isInstanceOf(DuplicatedUserIdException.class);
		}
	}

	@DisplayName("회원 아이디로 회원을 조회할 때")
	@Nested
	class UserFindTest {

		@DisplayName("해당 아이디를 가진 회원이 있으면 회원 정보를 반환한다.")
		@Test
		void givenUserId_whenFindByUserId_thenReturnsUser() {
			// given
			String userId = "uniqueId";
			userService.join(new JoinRequest(userId, "password", "name", "email@email.com"));

			// when & then
			assertThatCode(() -> userService.findByUserId(userId))
				.doesNotThrowAnyException();
		}

		@DisplayName("해당 아이디를 가진 회원이 없으면 예외를 던진다.")
		@Test
		void givenNotExistsUserId_whenFindByUserId_thenThrowsException() {
			// given
			String userId = "notExistsId";

			// when & then
			assertThatThrownBy(() -> userService.findByUserId(userId))
				.isInstanceOf(NotFoundException.class);
		}
	}
}
