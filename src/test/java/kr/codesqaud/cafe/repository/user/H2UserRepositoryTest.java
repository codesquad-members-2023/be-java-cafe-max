package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Sql("classpath:test.sql")
class H2UserRepositoryTest {

    H2UserRepository userRepository;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        this.userRepository = new H2UserRepository(jdbcTemplate);
    }

    @DisplayName("user를 save하면 jdbcRepository에 정상적으로 저장되는지 확인하는 테스트")
    @Test
    void save() {
        // given
        User user1 = new User("jdbcUser1", "password1", "jdbc1", "test1@email.com");
        User user2 = new User("jdbcUser2", "password2", "jdbc2", "test2@email.com");

        // when
        String savedUser1 = userRepository.save(user1);
        String savedUser2 = userRepository.save(user2);

        // then
        assertAll(
            () -> assertThat(savedUser1).isEqualTo(user1.getUserId()),
            () -> assertThat(savedUser2).isEqualTo(user2.getUserId()));
    }

    @DisplayName("userId가 일치하는 user를 가져오는지 확인하는 테스트")
    @Test
    void findByUserId() {
        // given
        User user1 = new User("jdbcUser1", "password1", "jdbc1", "test1@email.com");
        User user2 = new User("jdbcUser2", "password2", "jdbc2", "test2@email.com");
        String savedUser1 = userRepository.save(user1);
        String savedUser2 = userRepository.save(user2);

        // when
        User findUser1 = userRepository.findByUserId("jdbcUser1");
        User findUser2 = userRepository.findByUserId("jdbcUser2");

        // then
        assertAll(
            () -> assertThat(findUser1.getUserId()).isEqualTo(savedUser1),
            () -> assertThat(findUser2.getUserId()).isEqualTo(savedUser2),
            () -> assertThatThrownBy(() -> userRepository.findByUserId("none"))
                                                    .isInstanceOf(UserNotFoundException.class)
                                                    .hasMessage("존재하지 않는 회원입니다."));
    }

    @DisplayName("findAll을 통해 모든 user를 List로 가져오는지 확인하는 테스트")
    @Test
    void findAll() {
        // given
        User user1 = new User("jdbcUser1", "password1", "jdbc1", "test1@email.com");
        User user2 = new User("jdbcUser2", "password2", "jdbc2", "test2@email.com");
        String savedUser1 = userRepository.save(user1);
        String savedUser2 = userRepository.save(user2);

        // when
        List<User> allUsers = userRepository.findAll();

        // then
        assertAll(
            () -> assertThat(allUsers.get(0).getUserId()).isEqualTo(savedUser1),
            () -> assertThat(allUsers.get(1).getUserId()).isEqualTo(savedUser2),
            () -> assertThat(allUsers.size()).isEqualTo(2));
    }

    @DisplayName("userId를 통해 user가 존재하는지 확인하는 테스트")
    @Test
    void exist() {
        // given
        User user1 = new User("jdbcUser1", "password1", "jdbc1", "test1@email.com");
        String savedUser1 = userRepository.save(user1);

        // when
        boolean exist1 = userRepository.exist(savedUser1);
        boolean exist2 = userRepository.exist("none");

        // then
        assertAll(
            () -> assertThat(exist1).isEqualTo(true),
            () -> assertThat(exist2).isEqualTo(false));
    }

    @DisplayName("user를 update하면 수정한 정보가 jdbcRepository를 통해 DB에 정상적으로 반영되는지 확인하는 테스트")
    @Test
    void update() {
        // given
        User user1 = new User("jdbcUser1", "password1", "jdbc1", "test1@email.com");
        User user2 = new User("jdbcUser2", "password2", "jdbc2", "test2@email.com");
        String savedUser1 = userRepository.save(user1);

        // when
        User user = new User("jdbcUser1", "password2", "jdbc2", "test2@email.com");
        int update1 = userRepository.update(user);
        int update2 = userRepository.update(user2);

        // then
        User findUser = userRepository.findByUserId(savedUser1);

        assertAll(
            () -> assertThat(findUser.getUserId()).isEqualTo(user.getUserId()),
            () -> assertThat(findUser.getPassword()).isEqualTo(user.getPassword()),
            () -> assertThat(findUser.getName()).isEqualTo(user.getName()),
            () -> assertThat(findUser.getEmail()).isEqualTo(user.getEmail()),

            () -> assertThat(update1).isEqualTo(1),
            () -> assertThat(update2).isEqualTo(0));
    }
}
