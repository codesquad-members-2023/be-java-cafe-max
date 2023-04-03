package codesquad.cafe.service;

import codesquad.cafe.domain.User;
import codesquad.cafe.dto.UserRequestDto;
import codesquad.cafe.repository.MemoryUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    void showUsers() {
        // given

        // when

        // then
    }

    @Test
    void findUser() {
        // given

        // when

        // then
    }
}