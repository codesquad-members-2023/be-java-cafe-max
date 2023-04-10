package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.MemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryUserRepositoryTest {

    MemoryUserRepository memoryUserRepository;

    @BeforeEach
    void afterEach() {
        memoryUserRepository = new MemoryUserRepository();
    }

    @DisplayName("user를 save하면 memoryRepository에 정상적으로 저장되는지 확인하는 테스트")
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

    @DisplayName("findAll을 통해 모든 user를 List로 가져오는지 확인하는 테스트")
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
