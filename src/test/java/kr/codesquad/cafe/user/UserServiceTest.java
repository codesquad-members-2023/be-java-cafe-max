package kr.codesquad.cafe.user;

import kr.codesquad.cafe.user.domain.User;
import kr.codesquad.cafe.user.dto.JoinForm;
import kr.codesquad.cafe.user.dto.LoginForm;
import kr.codesquad.cafe.user.dto.ProfileEditForm;
import kr.codesquad.cafe.user.dto.UserForm;
import kr.codesquad.cafe.user.exception.DuplicateEmailException;
import kr.codesquad.cafe.user.exception.IncorrectPasswordException;
import kr.codesquad.cafe.user.exception.InvalidPasswordException;
import kr.codesquad.cafe.user.exception.UserNotFoundException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    public static final String JACK = "jack";
    public static final String JACK_EMAIL = "jack@email.com";
    public static final String TEST_PASSWORD = "password";
    public static final String JERRY_EMAIL = "jerry@email.com";
    public static final String JERRY = "jerry";
    public static final String NO_MATCH_PASSWORD = "123456789a";

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @DisplayName("새로운 유저를 저장기능 테스트")
    @Test
    void save() {
        assertThat(userRepository.findByEmail(JACK_EMAIL)).isEmpty();
        userService.save(new JoinForm(JACK, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD));
        assertThat(userRepository.findByEmail(JACK_EMAIL)).isPresent();
    }

    @DisplayName("모든 유저정보를 가져온다")
    @Test
    void getAllUsersForm() {
        User jack = userService.save(new JoinForm(JACK, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD));
        User jerry = userService.save(new JoinForm(JERRY, JERRY_EMAIL, TEST_PASSWORD, TEST_PASSWORD));
        List<UserForm> allUsersForm = userService.getAllUsersForm(0);
        assertThat(allUsersForm).contains(UserForm.from(jerry), UserForm.from(jack));
    }

    @DisplayName("유저 정보를 업로드한다")
    @Test
    void update() {
        User jack = userService.save(new JoinForm(JACK, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD));
        User update = userService.update(jack, new ProfileEditForm(JERRY, JERRY_EMAIL, TEST_PASSWORD));
        assertThat(update.getEmail()).isEqualTo(JERRY_EMAIL);
        assertThat(update.getNickname()).isEqualTo(JERRY);
    }

    @DisplayName("비밀번호 확인")
    @ParameterizedTest
    @CsvSource({TEST_PASSWORD + ",true", "asdsdfsdfs1,false"})
    void isSamePassword(String targetPassword, Boolean expect) {
        User jack = userService.save(new JoinForm(JACK, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD));
        assertThat(userService.isSamePassword(jack, targetPassword)).isEqualTo(expect);
    }

    @DisplayName("이메일로 User 조회")
    @Test
    void findByEmail() {
        User jack = userService.save(new JoinForm(JACK, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD));
        Optional<User> userOptional = userService.findByEmail(JACK_EMAIL);
        assertThat(userOptional).contains(jack);
    }

    @DisplayName("이메일주소 포함여부 확인")
    @Test
    void containsEmail() {
        assertThat(userService.existsByEmail(JACK_EMAIL)).isFalse();
        userService.save(new JoinForm(JACK, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD));
        assertThat(userService.existsByEmail(JACK_EMAIL)).isTrue();
    }

    @DisplayName("아이디로 User 찾기")
    @Test
    void findById() {
        User jack = userService.save(new JoinForm(JACK, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD));
        assertThat(userRepository.findById(jack.getId())).contains(jack);
    }


    @DisplayName("loginForm 정보를 체크")
    @Test
    void checkLoginForm() {
        User jack = userService.save(new JoinForm(JACK, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD));
        SoftAssertions.assertSoftly(softly -> {
            assertThatThrownBy(() -> userService.checkLoginForm(new LoginForm(JERRY_EMAIL, TEST_PASSWORD))).isInstanceOf(UserNotFoundException.class);
            assertThatThrownBy(() -> userService.checkLoginForm(new LoginForm(JACK_EMAIL, NO_MATCH_PASSWORD))).isInstanceOf(IncorrectPasswordException.class);
            assertThat(userService.checkLoginForm(new LoginForm(JACK_EMAIL, TEST_PASSWORD))).isSameAs(jack);
        });
    }

    @DisplayName("수정 ")
    @Test
    void check() {
        User jack = userService.save(new JoinForm(JACK, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD));
        userService.save(new JoinForm(JERRY, JERRY_EMAIL, TEST_PASSWORD, TEST_PASSWORD));
        SoftAssertions.assertSoftly(softly -> {
            assertThatThrownBy(() -> userService.checkEditInfo(jack, new ProfileEditForm(JACK, JERRY_EMAIL, TEST_PASSWORD))).isInstanceOf(DuplicateEmailException.class);
            assertThatThrownBy(() -> userService.checkEditInfo(jack, new ProfileEditForm(JACK, JACK_EMAIL, NO_MATCH_PASSWORD))).isInstanceOf(InvalidPasswordException.class);
            assertThatCode(() -> userService.checkEditInfo(jack, new ProfileEditForm(JACK, JACK_EMAIL, TEST_PASSWORD))).doesNotThrowAnyException();
        });
    }
}
