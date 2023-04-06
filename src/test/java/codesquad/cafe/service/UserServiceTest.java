package codesquad.cafe.service;

import codesquad.cafe.domain.user.domain.User;
import codesquad.cafe.domain.user.dto.UserRequestDto;
import codesquad.cafe.domain.user.dto.UserResponseDto;
import codesquad.cafe.domain.user.dto.UserUpdateRequestDto;
import codesquad.cafe.domain.user.repository.MemoryUserRepository;
import codesquad.cafe.domain.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest {

    MemoryUserRepository memoryUserRepository = new MemoryUserRepository();
    UserService userService = new UserService(memoryUserRepository);

    @Test
    @DisplayName("사용자 회원가입 테스트")
    void join() {
        // given
        UserRequestDto userRequestDto = createDummyUserDto1();

        // when
        User joinUser = userService.join(userRequestDto);

        // then
        assertThat(joinUser).usingRecursiveComparison().isEqualTo(userRequestDto.toEntity());
    }

    @Test
    @DisplayName("사용자 id 중복 시 예외")
    void checkDuplicatedUser() {
        // given
        UserRequestDto userRequestDto1 = createDummyUserDto1();
        UserRequestDto userRequestDto2 = createDummyUserDto2();

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
        UserRequestDto userRequestDto1 = createDummyUserDto1();
        UserRequestDto userRequestDto2 = createDummyUserDto2();
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
        UserRequestDto userRequestDto = createDummyUserDto1();
        userService.join(userRequestDto);
        User user = userRequestDto.toEntity();

        // when
        UserResponseDto userResponseDto = userService.findUser(id);

        // then
        assertThat(new UserResponseDto(user.getId(), user.getName(), user.getEmail())).usingRecursiveComparison().isEqualTo(userResponseDto);
    }

    @Test
    @DisplayName("사용자 id로 사용자를 찾을 수 없으면 예외")
    void checkUser() {
        // given
        UserRequestDto userRequestDto = createDummyUserDto1();
        userService.join(userRequestDto);

        // when
        String id = "시오";

        // then
        Assertions.assertThrows(IllegalStateException.class,
                () -> userService.findUser(id));
    }

    @Test
    @DisplayName("사용자 id로 사용자를 찾아 사용자 정보 업데이트하기 테스트")
    void updateUser() {
        // given
        UserRequestDto userRequestDto = createDummyUserDto1();
        userService.join(userRequestDto);

        // when
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("1234", "1111", "시오", "siioo@gmail.com");
        userService.updateUser("sio", userUpdateRequestDto);

        // then
        assertThat(userService.findUser("sio")).usingRecursiveComparison().isEqualTo(new UserResponseDto("sio", "시오", "siioo@gmail.com"));
    }

    private UserRequestDto createDummyUserDto1() {
        return new UserRequestDto("sio", "1234", "sio", "sio@gmail.com");
    }

    private UserRequestDto createDummyUserDto2() {
        return new UserRequestDto("sio", "1111", "sioooo", "sioooo@gmail.com");
    }
}