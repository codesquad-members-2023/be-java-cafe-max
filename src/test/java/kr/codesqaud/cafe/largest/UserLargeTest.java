package kr.codesqaud.cafe.largest;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.codesqaud.cafe.domain.user.controller.UserController;
import kr.codesqaud.cafe.domain.user.dto.request.UserSaveRequestDto;
import kr.codesqaud.cafe.domain.user.dto.request.UserUpdateRequestDto;
import kr.codesqaud.cafe.domain.user.dto.response.UserDetailResponseDto;
import kr.codesqaud.cafe.domain.user.entity.User;
import kr.codesqaud.cafe.domain.user.repository.UserRepository;

@SpringBootTest
public class UserLargeTest {

	@Autowired
	private UserController userController;

	@Autowired
	private UserRepository userRepository;

	@DisplayName("회원 가입과 수정")
	@TestFactory
	Stream<DynamicTest> signInAndUpdate() {
		return Stream.of(
			dynamicTest("회원 가입", () -> {
				//given
				UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("pjs970616", "June", "a846548",
					"pjs970616@naver.com");

				//when
				userRepository.save(userSaveRequestDto.toEntity());

				//then
				assertThat(userRepository.findById("pjs970616")).isNotNull();
			}),

			dynamicTest("프로필 확인", () -> {
				//given
				Optional<User> user = userRepository.findById("pjs970616");

				//when
				UserDetailResponseDto userDetailResponseDto = new UserDetailResponseDto(user.get());

				//then
				assertThat(userDetailResponseDto.getName()).isEqualTo("June");
			}),
			dynamicTest("회원 수정 시 기존 비밀번호가 아닌 비밀번호를 입력한 경우", () -> {
				//given
				UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("wrongPassword", "Joonsoo",
					"June@codesquad.kr");

				// when & then
				assertThatThrownBy(() -> userController.modifyUserProfile(userUpdateRequestDto, "pjs970616"))
					.isInstanceOf(RuntimeException.class);
			}),
			dynamicTest("회원 수정", () -> {
				//given
				UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("a846548", "Joonsoo",
					"June@codesquad.kr");

				//when
				userRepository.update(userUpdateRequestDto.toEntity("pjs970616"));

				//then
				assertThat(userRepository.findById("pjs970616").get().getName()).isEqualTo("Joonsoo");
			}),
			dynamicTest("모든 회원 정보 조회", () -> {
				//given
				List<User> users = userRepository.findAll();
				List<UserDetailResponseDto> userDetailResponseDtos = new ArrayList<>();

				//when
				users.forEach(user -> userDetailResponseDtos.add(new UserDetailResponseDto(user)));

				//then
				assertThat(userDetailResponseDtos.get(0).getName()).isEqualTo("Joonsoo");

			})

		);
	}
}
