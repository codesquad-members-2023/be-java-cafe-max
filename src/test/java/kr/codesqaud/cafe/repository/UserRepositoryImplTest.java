package kr.codesqaud.cafe.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import kr.codesqaud.cafe.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
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
        User user = new User("test", "12345678", "test4", "test4@gmail.com");

        // when
        userRepository.save(user);

        // then
        User findUser = userRepository.findById("test").get();
        Assertions.assertThat(findUser.getUserId()).isEqualTo(user.getUserId()); // TODO: Id 말고 다른걸로 확인해야 할까
    }

    @Test
    @DisplayName("회원 ID로 가입 정보를 찾을 수 있다.")
    void findById() {
        // given
        User honux = new User("honux", "12345678", "honux", "honux@gmail.com");
        User crong = new User("crong", "12345678", "crong", "crong@gmail.com");

        // when
        userRepository.save(honux);
        userRepository.save(crong);

        // then
        User result = userRepository.findById("honux").get();
        assertThat(result.getUserId()).isEqualTo(honux.getUserId());
        assertThat(result.getUserId()).isNotEqualTo(crong.getUserId());
    }
}
