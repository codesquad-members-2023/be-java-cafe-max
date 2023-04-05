package kr.codesqaud.cafe;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


class UserRepositoryTest {
    UserRepository userRepository = new UserRepository();

    @Test
    @DisplayName("유저 저장소에 유저가 제대로 추가된다.")
    void saveTest() {
        User user = new User();
        user.setUserId("nag");
        user.setUserName("name");
        user.setEmail("asdf@gmail.com");
        user.setPassword("1234");

        userRepository.save(user);
        List<User> users = userRepository.getAllUsers();

        assertThat(users).contains(user);
    }
}