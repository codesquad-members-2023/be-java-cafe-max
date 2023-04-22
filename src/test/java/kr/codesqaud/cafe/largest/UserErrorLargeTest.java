package kr.codesqaud.cafe.largest;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.util.stream.Stream;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.codesqaud.cafe.domain.auth.controller.AuthController;
import kr.codesqaud.cafe.domain.auth.dto.request.AuthLoginRequestDto;
import kr.codesqaud.cafe.domain.user.controller.UserController;
import kr.codesqaud.cafe.domain.user.dto.request.UserSaveRequestDto;
import kr.codesqaud.cafe.domain.user.dto.request.UserUpdateRequestDto;
import kr.codesqaud.cafe.domain.user.entity.User;
import kr.codesqaud.cafe.domain.user.repository.UserRepository;
import kr.codesqaud.cafe.global.exception.user.DuplicatedUserIdException;
import kr.codesqaud.cafe.global.exception.user.InvalidLoginInfoException;
import kr.codesqaud.cafe.global.exception.user.InvalidPasswordException;

@SpringBootTest
public class UserErrorLargeTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserController userController;

	@Autowired
	private AuthController authController;

	@Autowired
	HttpSession httpSession;

	@DisplayName("회원가입 ,로그인, 수정 시 오류")
	@TestFactory
	Stream<DynamicTest> userError() {
		return Stream.of(
			dynamicTest("회원 가입 시 동일한 ID가 있을 경우", () -> {
				//given
				userRepository.save(new User("pjs970616", "June", "1234", "pjs970616@naver.com"));

				//when & then
				assertThatThrownBy(() -> userController.signIn(
					new UserSaveRequestDto("pjs970616", "Joonsoo", "1234", "Joonsoo@naver.com"))).isInstanceOf(
					DuplicatedUserIdException.class);

			}),
			dynamicTest("로그인시 올바르지 않은 아이디를 입력한 경우", () -> {
				//when & then
				assertThatThrownBy(() ->
					authController.login(new AuthLoginRequestDto("wrongId", "1234"),
						httpSession)).isInstanceOf(
					InvalidLoginInfoException.class);
			}),
                dynamicTest("로그인시 올바르지 않은 비밀번호를 입력한 경우", () -> {
                    //when & then
                    AuthLoginRequestDto requestDto = new AuthLoginRequestDto("pjs970616", "wrongPassword");
                    assertThatThrownBy(() -> authController.login(requestDto, httpSession))
                            .isInstanceOf(InvalidLoginInfoException.class);
                }),
			dynamicTest("회원 정보 수정시 올바르지 않은 비밀번호를 입력한 경우", () -> {
				//when & then
				assertThatThrownBy(() -> userController.modifyUserProfile(
					new UserUpdateRequestDto("wrongPassword", "Joonsoo", "joonsoo@codesquad.kr"),
					"pjs970616")).isInstanceOf(
					InvalidPasswordException.class);
			})

		);
	}
}
