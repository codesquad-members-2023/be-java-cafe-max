package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.common.exception.CommonException;
import kr.codesqaud.cafe.common.exception.user.UserLoginException;
import kr.codesqaud.cafe.controller.dto.user.UserLoginDto;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @DisplayName("회원 조회 실패 시 CommonException이 발생한다.")
    @Test
    void findUserFailTest() {
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.find(1L)).isInstanceOf(CommonException.class);
    }

    @DisplayName("로그인시 비밀번호가 일치하면 정상적으로 로그인이 된다.")
    @Test
    void loginSuccessTest() {
        final User user = new User("testId", "이성빈", "test123123", "tjdqls@naver.com");
        final UserLoginDto userLoginDto = new UserLoginDto("testId", "test123123");

        given(userRepository.findByUserId(userLoginDto.getUserId())).willReturn(Optional.of(user));

        assertThatNoException().isThrownBy(() -> userService.login(userLoginDto));
    }

    @DisplayName("로그인시 비밀번호가 다르면 UserLoginException 예외가 발생한다.")
    @Test
    void loginFailTest() {
        final User user = new User("testId", "이성빈", "test123123", "tjdqls@naver.com");
        final UserLoginDto userLoginDto = new UserLoginDto("testId", "adsadkdjalk");

        given(userRepository.findByUserId(userLoginDto.getUserId())).willReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.login(userLoginDto)).isInstanceOf(UserLoginException.class);
    }
}