package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.service.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemoryUserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository = new MemoryUserRepository();
    }

    @Test
    @DisplayName("객체 정상 저장 여부 확인")
    void save() {
        // given
        userRepository.save(new User(null, "user1", "1234", "test1", "test@test.com"));

        // when
        List<User> users = userRepository.findAll();

        // then
        assertEquals(1, users.size());
        assertEquals(users.get(0), new User(1L, "user1", "1234", "test1", "test@test.com"));
    }

    @Test
    @DisplayName("객체 순차 저장 확인")
    void save_many() {
        // given
        userRepository.save(new User(null, "user1", "1234", "test1", "test@test.com"));
        userRepository.save(new User(null, "user2", "1234", "test2", "test2@test.com"));

        // when
        List<User> users = userRepository.findAll();
        Optional<User> user1 = userRepository.findByUserId("user1");
        Optional<User> user2 = userRepository.findByUserId("user2");

        // then
        assertEquals(2, users.size());
        assertTrue(user1.isPresent());
        assertTrue(user2.isPresent());
        assertEquals(user1.get(), new User(1L, "user1", "1234", "test1", "test@test.com"));
        assertEquals(user2.get(), new User(2L, "user2", "1234", "test2", "test2@test.com"));
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