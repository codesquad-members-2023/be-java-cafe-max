package kr.codesqaud.cafe;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



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

        Assertions.assertThat(userRepository.getRepository()).contains(user);
    }
}