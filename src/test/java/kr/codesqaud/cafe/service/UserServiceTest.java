package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.common.exception.CommonException;
import kr.codesqaud.cafe.common.exception.user.UserLoginException;
import kr.codesqaud.cafe.controller.dto.user.UserLoginDto;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @DisplayName("회원 조회 실패 시 CommonException이 발생한다.")
    @Test
    void findUserFailTest() {
        BDDMockito.given(userRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.find(1L)).isInstanceOf(CommonException.class);
    }

    @DisplayName("로그인시 비밀번호가 일치하면 정상적으로 로그인이 된다.")
    @Test
    void loginSuccessTest() {
        final User user = new User("testId", "이성빈", "test123123", "tjdqls@naver.com");
        final UserLoginDto userLoginDto = new UserLoginDto("testId", "test123123");

        BDDMockito.given(userRepository.findByUsername(userLoginDto.getUsername())).willReturn(Optional.of(user));

        Assertions.assertThatNoException().isThrownBy(() -> userService.login(userLoginDto));
    }

    @DisplayName("로그인시 비밀번호가 다르면 UserLoginException 예외가 발생한다.")
    @Test
    void loginFailTest() {
        final User user = new User("testId", "이성빈", "test123123", "tjdqls@naver.com");
        final UserLoginDto userLoginDto = new UserLoginDto("testId", "adsadkdjalk");

        BDDMockito.given(userRepository.findByUsername(userLoginDto.getUsername())).willReturn(Optional.of(user));

        Assertions.assertThatThrownBy(() -> userService.login(userLoginDto)).isInstanceOf(UserLoginException.class);
    }
}