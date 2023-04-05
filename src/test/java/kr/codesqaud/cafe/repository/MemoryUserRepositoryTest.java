package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.MemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryUserRepositoryTest {

    MemoryUserRepository memoryUserRepository = new MemoryUserRepository();

    @BeforeEach
    void afterEach() {
        memoryUserRepository = new MemoryUserRepository();
    }

    @Test
    void save() {
        // given
        User user = new User("testId", "testPassword", "testName", "testEmail");
        memoryUserRepository.save(user);

        // when
        User findUser = memoryUserRepository.findByUserId(user.getUserId());

        // then
        assertThat(findUser).isEqualTo(user);
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
