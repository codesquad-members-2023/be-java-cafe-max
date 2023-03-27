package kr.codesqaud.cafe.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.codesqaud.cafe.controller.dto.req.JoinRequest;
import kr.codesqaud.cafe.exception.DuplicatedUserIdException;

@SpringBootTest
class UserServiceTest {

	private final UserService userService;

	@Autowired
	public UserServiceTest(UserService userService) {
		this.userService = userService;
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
}
