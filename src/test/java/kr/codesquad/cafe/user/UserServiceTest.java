package kr.codesquad.cafe.user;

import kr.codesquad.cafe.user.domain.User;
import kr.codesquad.cafe.user.dto.JoinForm;
import kr.codesquad.cafe.user.dto.LoginForm;
import kr.codesquad.cafe.user.dto.ProfileEditForm;
import kr.codesquad.cafe.user.dto.UserForm;
import kr.codesquad.cafe.user.exception.DuplicateEmailException;
import kr.codesquad.cafe.user.exception.ExistsEmailException;
import kr.codesquad.cafe.user.exception.IncorrectPasswordException;
import kr.codesquad.cafe.user.exception.InvalidPasswordException;
import kr.codesquad.cafe.user.exception.UserNotFoundException;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final String JACK = "jack";
    public static final String JACK_EMAIL = "jack@email.com";
    public static final String TEST_PASSWORD = "password";
    public static final String JERRY_EMAIL = "jerry@email.com";
    public static final String NO_MATCH_PASSWORD = "123456789a";
    public static final String NOT_RIGHT_PASSWORD = "asdsdfsdfs1";

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    StringEncryptor stringEncryptor;

    @DisplayName("유저를 저장할 때 repository에 유저 이메일이 존재하지 않으면 정상으로 저장한다.")
    @Test
    void saveSuccess() {
        JoinForm joinForm = mock(JoinForm.class);
        User user = mock(User.class);
        given(joinForm.toUser(any())).willReturn(user);
        given(userRepository.existsByEmail(any(String.class))).willReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        given(joinForm.getEmail()).willReturn(JACK_EMAIL);

        User save = userService.save(joinForm);

        assertThat(save).usingRecursiveComparison().isEqualTo(user);
        verify(userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @DisplayName("유저를 저장할 때 repository에 유저 이메일이 존재하면 에러를 던진다.")
    @Test
    void saveFailed() {
        JoinForm joinForm = mock(JoinForm.class);
        given(joinForm.getEmail()).willReturn(JACK_EMAIL);
        given(userRepository.existsByEmail(any(String.class))).willReturn(true);

        assertThatThrownBy(() -> userService.save(joinForm)).isInstanceOf(ExistsEmailException.class);
    }

    @DisplayName("repository 에서 모든 유저정보를 가져온다")
    @Test
    void getAllUsersForm() {
        Page<User> users = mock(Page.class);
        List<UserForm> userForms = mock(List.class);
        given(userRepository.findAll(any(Pageable.class))).willReturn(users);

        List<UserForm> allUsersForm = userService.getAllUsersForm(0);

        assertThat(allUsersForm).usingRecursiveComparison().isEqualTo(userForms);
        verify(userRepository, times(1)).findAll(any(Pageable.class));
        verifyNoMoreInteractions(userRepository);
    }

    @DisplayName("유저 정보를 업로드는 repository에 위임한다")
    @Test
    void update() {
        User user = mock(User.class);
        User edit = mock(User.class);
        ProfileEditForm profileEditForm = mock(ProfileEditForm.class);
        given(profileEditForm.setUser(any(User.class))).willReturn(edit);
        given(userRepository.save(any(User.class))).willReturn(edit);

        User update = userService.update(user, profileEditForm);

        assertThat(update).usingRecursiveComparison().isEqualTo(edit);
        verify(profileEditForm, times(1)).setUser(any(User.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("유저의 비밀번호 확인은 유저 객체에 위임한다")
    @ParameterizedTest
    @ValueSource(strings = {"true", "false"})
    void isSamePassword(Boolean expect) {
        User user = mock(User.class);
        given(user.isSamePassword(any(StringEncryptor.class), any(String.class))).willReturn(expect);

        assertThat(userService.isSamePassword(user, TEST_PASSWORD)).isEqualTo(expect);

        verify(user, times(1)).isSamePassword(any(StringEncryptor.class), any(String.class));
    }

    @DisplayName("이메일 조회는 repository에 위임한다")
    @Test
    void findByEmail() {
        User user = mock(User.class);
        given(userRepository.findByEmail(any(String.class))).willReturn(Optional.ofNullable(user));

        Optional<User> userOptional = userService.findByEmail(JACK_EMAIL);

        assertThat(userOptional).usingRecursiveComparison().isEqualTo(Optional.ofNullable(user));
        verify(userRepository, times(1)).findByEmail(any(String.class));
    }

    @DisplayName("이메일주소 포함여부는 repository에 위암한다")
    @ParameterizedTest
    @ValueSource(strings = "true,false")
    void existsByEmail(boolean except) {
        given(userRepository.existsByEmail(any(String.class))).willReturn(except);

        assertThat(userService.existsByEmail(JACK_EMAIL)).isEqualTo(except);

        verify(userRepository, times(1)).existsByEmail(any(String.class));
    }

    @DisplayName("loginForm 정보에서 이메일이 존재하지 않으면 UserNotFoundException 을 에러를 던진다")
    @Test
    void checkLoginFormFailedByEmail() {
        given(userRepository.findByEmail(any(String.class))).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.checkLoginForm(new LoginForm(JACK_EMAIL, NOT_RIGHT_PASSWORD)))
                .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("loginForm 정보에서 password 가 일치하지 않으면 IncorrectPasswordException 을 에러를 던진다")
    @Test
    void checkLoginFormFailedByPassword() {
        User user = mock(User.class);
        given(userRepository.findByEmail(any(String.class))).willReturn(Optional.ofNullable(user));
        given(user.isSamePassword(any(StringEncryptor.class), any(String.class))).willReturn(false);

        assertThatThrownBy(() -> userService.checkLoginForm(new LoginForm(JACK_EMAIL, NOT_RIGHT_PASSWORD)))
                .isInstanceOf(IncorrectPasswordException.class);
    }

    @DisplayName("loginForm 정보에서 password 가 일치하지 않으면 IncorrectPasswordException 을 에러를 던진다")
    @Test
    void checkLoginFormSuccess() {
        User user = mock(User.class);
        given(userRepository.findByEmail(any(String.class))).willReturn(Optional.ofNullable(user));
        given(user.isSamePassword(any(StringEncryptor.class), any(String.class))).willReturn(true);

        User targetUser = userService.checkLoginForm(new LoginForm(JACK_EMAIL, NOT_RIGHT_PASSWORD));

        assertThat(targetUser).usingRecursiveComparison().isEqualTo(user);
        verify(userRepository, times(1)).findByEmail(any(String.class));
        verify(user, times(1)).isSamePassword(any(StringEncryptor.class), any(String.class));
    }


    @DisplayName("유저의 변경하려고 하는 이메일이 기존 이메일과 같지 않고 repository에 존재하면 DuplicateEmailException 을 던진다.")
    @Test
    void checkEditInfoFailedByEmail() {
        User user = mock(User.class);
        given(user.isSameEmail(any(String.class))).willReturn(false);
        given(userRepository.existsByEmail(any(String.class))).willReturn(true);

        assertThatThrownBy(() -> userService.checkEditInfo(user, new ProfileEditForm(JACK, JERRY_EMAIL, TEST_PASSWORD)))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @DisplayName("repostiory에 이메일이 존재하지 않고 비번이 일치하지 않으면 InvalidPasswordException 을 던진다.")
    @Test
    void checkEditInfoFailedByPasswordAndNoExistEmail() {
        User user = mock(User.class);
        given(user.isSameEmail(any(String.class))).willReturn(false);
        given(userRepository.existsByEmail(any(String.class))).willReturn(false);
        given(user.isSamePassword(any(StringEncryptor.class), any(String.class))).willReturn(false);

        assertThatThrownBy(() -> userService.checkEditInfo(user, new ProfileEditForm(JACK, JACK_EMAIL, NO_MATCH_PASSWORD)))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @DisplayName("이메일이 변경되지 않았고 비번이 일치하지 않으면 InvalidPasswordException 을 던진다.")
    @Test
    void checkEditInfoFailedByPasswordAndSameEmail() {
        User user = mock(User.class);
        given(user.isSameEmail(any(String.class))).willReturn(true);
        given(user.isSamePassword(any(StringEncryptor.class), any(String.class))).willReturn(false);

        assertThatThrownBy(() -> userService.checkEditInfo(user, new ProfileEditForm(JACK, JACK_EMAIL, NO_MATCH_PASSWORD)))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @DisplayName("이메일이 중복되지 않고 비번도 일치하면 아무일도 발생하지 않는다.")
    @Test
    void checkEditInfoSuccess() {
        User user = mock(User.class);
        given(user.isSameEmail(any(String.class))).willReturn(true);
        given(user.isSamePassword(any(StringEncryptor.class), any(String.class))).willReturn(true);

        assertThatCode(() -> userService.checkEditInfo(user, new ProfileEditForm(JACK, JACK_EMAIL, NO_MATCH_PASSWORD)))
                .doesNotThrowAnyException();
    }
}
