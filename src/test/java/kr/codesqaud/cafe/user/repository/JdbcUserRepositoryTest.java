package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.service.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
class JdbcUserRepositoryTest {

    private UserRepository userRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void init() {
        userRepository = new JdbcUserRepository(dataSource);
    }

    @Test
    @DisplayName("객체 순차 저장 확인")
    void save_many() {
        // given
        User user1 = userRepository.save(new User(null, "user1", "1234", "test1", "test@test.com"));
        User user2 = userRepository.save(new User(null, "user2", "1234", "test2", "test2@test.com"));

        // when
        List<User> users = userRepository.findAll();
        long id1 = user1.getId();
        long id2 = user2.getId();

        // then
        assertEquals(2, users.size());
        assertEquals(1, id2 - id1);
        assertEquals(user1, new User(id1, "user1", "1234", "test1", "test@test.com"));
        assertEquals(user2, new User(id2, "user2", "1234", "test2", "test2@test.com"));
    }

    @Test
    @DisplayName("없는 유저 아이디로 가져오는 경우")
    void findByUserId() {
        // given
        userRepository.save(new User(null, "user1", "1234", "test1", "test@test.com"));

        // when
        Optional<User> user = userRepository.findByUserId("user2");

        // then
        assertTrue(user.isEmpty());
    }
}