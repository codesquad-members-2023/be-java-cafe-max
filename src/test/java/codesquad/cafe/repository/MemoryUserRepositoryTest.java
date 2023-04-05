package codesquad.cafe.repository;

import codesquad.cafe.domain.user.domain.User;
import codesquad.cafe.domain.user.repository.MemoryUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

    MemoryUserRepository memoryUserRepository = new MemoryUserRepository();

    @Test
    @DisplayName("사용자 데이터 저장 테스트")
    void save() {
        // given
        User user = new User("sio", "1234", "sio","sio@gmail.com");

        // when
        User savedUser = memoryUserRepository.save(user);

        // then
        assertThat(user).isEqualTo(savedUser);
    }

    @Test
    @DisplayName("사용자 id로 사용자 찾기 테스트")
    void findById() {
        // given
        User user = new User("sio", "1234", "sio","sio@gmail.com");
        memoryUserRepository.save(user);
        String id = "sio";

        // when
        User foundUser = memoryUserRepository.findById(id).get();

        // then
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    @DisplayName("사용자 목록 조회 테스트")
    void findAll() {
        // given
        User user1 = new User("sio", "1234", "sio","sio@gmail.com");
        User user2 = new User("시오", "1234", "시오","siooo@gmail.com");
        memoryUserRepository.save(user1);
        memoryUserRepository.save(user2);

        // when
        List<User> users = memoryUserRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
    }
}