package codesquad.cafe.service;

import codesquad.cafe.domain.User;
import codesquad.cafe.dto.UserRequestDto;
import codesquad.cafe.dto.UserResponseDto;
import codesquad.cafe.repository.MemoryUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    MemoryUserRepository memoryUserRepository = new MemoryUserRepository();
    UserService userService = new UserService(memoryUserRepository);

    @Test
    @DisplayName("사용자 회원가입 테스트")
    void join() {
        // given
        UserRequestDto userRequestDto = new UserRequestDto("sio", "1234", "sio", "sio@gmail.com");

        // when
        User joinUser = userService.join(userRequestDto);

        // then
        assertThat(joinUser).usingRecursiveComparison().isEqualTo(userRequestDto.toEntity());
    }

    @Test
    @DisplayName("사용자 id 중복 시 예외")
    void checkDuplicatedUser() {
        // given
        UserRequestDto userRequestDto1 = new UserRequestDto("sio", "1234", "sio", "sio@gmail.com");
        UserRequestDto userRequestDto2 = new UserRequestDto("sio", "1111", "sioooo", "sioooo@gmail.com");

        // when
        userService.join(userRequestDto1);

        // then
        Assertions.assertThrows(IllegalStateException.class,
                () -> userService.join(userRequestDto2));
    }

    @Test
    @DisplayName("사용자 회원가입 시 목록에 정상적으로 들어가는지 테스트")
    void showUsers() {
        // given
        UserRequestDto userRequestDto1 = new UserRequestDto("sio", "1234", "sio", "sio@gmail.com");
        UserRequestDto userRequestDto2 = new UserRequestDto("시오", "1111", "시오", "sioooo@gmail.com");
        userService.join(userRequestDto1);
        userService.join(userRequestDto2);

        List<UserResponseDto> userResponse = new ArrayList<>();
        userResponse.add(new UserResponseDto("sio", "sio", "sio@gmail.com"));
        userResponse.add(new UserResponseDto("시오", "시오", "sioooo@gmail.com"));

        // when
        List<UserResponseDto> userResponseDtos = userService.showUsers();

        // then
        assertThat(userResponseDtos.size()).isEqualTo(userResponse.size());
    }

    @Test
    @DisplayName("사용자 id로 사용자 찾기 테스트")
    void findUser() {
        // given
        String id = "sio";
        UserRequestDto userRequestDto = new UserRequestDto("sio", "1234", "sio", "sio@gmail.com");
        userService.join(userRequestDto);
        User user = userRequestDto.toEntity();

        // when
        UserResponseDto userResponseDto = userService.findUser(id);

        // then
        assertThat(new UserResponseDto(user.getId(), user.getName(), user.getEmail())).usingRecursiveComparison().isEqualTo(userResponseDto);
    }
}