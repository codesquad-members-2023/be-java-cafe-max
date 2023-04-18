package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.repository.JdbcUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    private static final String USER_ID = "johndoe";
    private static final String PASSWORD = "password";
    private static final String NAME = "John Doe";
    private static final String EMAIL = "johndoe@example.com";

    @Autowired
    private JdbcUserRepository userRepository;

    @Test
    @DisplayName("새로운 사용자를 저장하면 id로 찾을 수 있다")
    public void test_save() {
        // given
        User user = new User(USER_ID, PASSWORD, NAME, EMAIL);

        // when
        String savedUserId = userRepository.save(user);

        // then
        Optional<User> savedUser = userRepository.findById(savedUserId);
        assertThat(savedUser.isPresent()).isTrue();
        assertThat(savedUser.get().getUserId()).isEqualTo(USER_ID);
        assertThat(savedUser.get().getPassword()).isEqualTo(PASSWORD);
        assertThat(savedUser.get().getName()).isEqualTo(NAME);
        assertThat(savedUser.get().getEmail()).isEqualTo(EMAIL);
    }

    @Test
    @DisplayName("유저를 저장하고 findAll을 하면 저장된 유저 수만큼 가져온다")
    public void test_findAll() {
        // given
        User user = new User(USER_ID, PASSWORD, NAME, EMAIL);
        userRepository.save(user);
        User user2 = new User(USER_ID + 1, PASSWORD, NAME, EMAIL);
        userRepository.save(user2);

        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("특정 사용자를 ID로 검색할 수 있다.")
    public void test_findById() {
        // given
        User user = new User(USER_ID, PASSWORD, NAME, EMAIL);
        String savedUserId = userRepository.save(user);

        // when
        Optional<User> actualUser = userRepository.findById(savedUserId);

        // then
        assertThat(actualUser.isPresent()).isTrue();
        assertThat(actualUser.get().getUserId()).isEqualTo(USER_ID);
        assertThat(actualUser.get().getPassword()).isEqualTo(PASSWORD);
        assertThat(actualUser.get().getName()).isEqualTo(NAME);
        assertThat(actualUser.get().getEmail()).isEqualTo(EMAIL);
    }

}
