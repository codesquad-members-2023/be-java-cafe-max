package kr.codesquad.cafe.user;

import kr.codesquad.cafe.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    public static final String JACK_NICKNAME = "jack";
    public static final String JACK_EMAIL = "jack@email.com";
    public static final String TEST_PASSWORD = "123456789a";
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager entityManager;

    @DisplayName("이메일로 User 조회")
    @Test
    void findByEmail() {
        User user = new User.Builder()
                .email(JACK_EMAIL)
                .nickname(JACK_NICKNAME)
                .password(TEST_PASSWORD)
                .build();

        assertThat(userRepository.findByEmail(JACK_EMAIL)).isEmpty();

        userRepository.save(user);

        assertThat(userRepository.findByEmail(JACK_EMAIL)).isPresent();
    }

    @DisplayName("이메일로 유저 존재여부 확인")
    @Test
    void existsByEmail() {
        User user = new User.Builder()
                .email(JACK_EMAIL)
                .nickname(JACK_NICKNAME)
                .password(TEST_PASSWORD)
                .build();

        assertThat(userRepository.existsByEmail(JACK_EMAIL)).isFalse();

        userRepository.save(user);

        assertThat(userRepository.existsByEmail(JACK_EMAIL)).isTrue();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        entityManager.flush();
    }
}
