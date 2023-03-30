package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryUserRepositoryTest {

    MemoryUserRepository memoryUserRepository = new MemoryUserRepository();

    @AfterEach
    void afterEach() {
        memoryUserRepository.clearStore();
    }

    @Test
    void save() {
        // given
        User user = new User("testId", "testPassword", "testName", "testEmail");
        User savedUser = memoryUserRepository.save(user);

        // when
        User findUser = memoryUserRepository.findByUserId(user.getUserId());

        // then
        assertThat(findUser).isEqualTo(savedUser);
    }

    @Test
    void findAll() {
        // given
        User user1 = new User("testId1", "testPassword1", "testName1", "testEmail1");
        User user2 = new User("testId2", "testPassword2", "testName2", "testEmail2");

        memoryUserRepository.save(user1);
        memoryUserRepository.save(user2);

        // when
        List<User> allUser = memoryUserRepository.findAll();

        // then
        assertThat(allUser.size()).isEqualTo(2);
        assertThat(allUser).contains(user1, user2);
    }
}
