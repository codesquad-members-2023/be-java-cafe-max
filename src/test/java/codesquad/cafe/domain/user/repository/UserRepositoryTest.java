package codesquad.cafe.domain.user.repository;

import codesquad.cafe.domain.user.domain.User;
import codesquad.cafe.domain.user.repository.MemoryUserRepository;
import codesquad.cafe.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest {

    UserRepository userRepository = new MemoryUserRepository();

    @Test
    @DisplayName("사용자 데이터 저장 테스트")
    void save() {
        // given
        User user = createDummyUser1();

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(user).isEqualTo(savedUser);
    }

    @Test
    @DisplayName("사용자 id로 사용자 찾기 테스트")
    void findById() {
        // given
        User user = createDummyUser1();
        userRepository.save(user);
        String id = "sio";

        // when
        User foundUser = userRepository.findById(id).get();

        // then
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    @DisplayName("사용자 목록 조회 테스트")
    void findAll() {
        // given
        User user1 = createDummyUser1();
        User user2 = createDummyUser2();
        userRepository.save(user1);
        userRepository.save(user2);

        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
    }

    private User createDummyUser1() {
        return new User("sio", "1234", "sio","sio@gmail.com");
    }

    private User createDummyUser2() {
        return new User("시오", "1234", "시오","siooo@gmail.com");
    }
}