package kr.codesquad.cafe.user;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import kr.codesquad.cafe.user.domain.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

	private static final String JACK_NICKNAME = "jack";
	private static final String JACK_EMAIL = "jack@email.com";
	private static final String TEST_PASSWORD = "123456789a";

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
