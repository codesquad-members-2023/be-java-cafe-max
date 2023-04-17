package kr.codesqaud.cafe;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


class MemoryUserRepositoryTest {
    MemoryUserRepository memoryUserRepository = new MemoryUserRepository();

    @Test
    @DisplayName("유저 저장소에 유저가 제대로 추가된다.")
    void saveTest() {
        User user = User.builder().build();

        memoryUserRepository.save(user);
        List<User> users = memoryUserRepository.getAllUsers();

        assertThat(users).contains(user);
    }
}