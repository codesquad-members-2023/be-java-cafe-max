package kr.codesqaud.cafe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.codesqaud.cafe.user.User;
import kr.codesqaud.cafe.user.UserRepository;
import kr.codesqaud.cafe.user.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryImplTest {

    UserRepository userRepository;

    @Autowired
    NamedParameterJdbcTemplate template;

    @BeforeEach
    void beforeEach() {
        userRepository = new UserRepositoryImpl(template);
    }

    @Test
    @DisplayName("회원 가입을 하면 회원 정보가 저장된다.")
    void save() {
        // given
        User user = new User("test", "12345678", "test", "tes4@gmail.com");

        // when
        userRepository.save(user);

        // then
        User findUser = userRepository.findById("test").get();
        assertThat(findUser.getLoginId()).isEqualTo(user.getLoginId());
        assertThat(findUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(findUser.getName()).isEqualTo(user.getName());
        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("로그인 ID로 가입 정보를 찾을 수 있다.")
    void findById() {
        // given
        User honux = new User("honux", "12345678", "honux", "honux@gmail.com");
        User crong = new User("crong", "12345678", "crong", "crong@gmail.com");

        // when
        userRepository.save(honux);
        userRepository.save(crong);

        // then
        User result = userRepository.findById("honux").get();
        assertThat(result.getLoginId()).isEqualTo(honux.getLoginId());
        assertThat(result.getLoginId()).isNotEqualTo(crong.getLoginId());
    }

    @Test
    @DisplayName("모든 회원가입 정보를 찾을 수 있다.")
    void findAll() {
        // given
        User honux = new User("honux", "12345678", "honux", "honux@gmail.com");
        User crong = new User("crong", "12345678", "crong", "crong@gmail.com");

        // when
        userRepository.save(honux);
        userRepository.save(crong);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(2);
    }
}
