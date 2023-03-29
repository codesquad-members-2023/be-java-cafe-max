package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;

class PureUserRepositoryImplTest {
    UserRepository userRepository = new PureUserRepositoryImpl();

    @DisplayName("동시에 많은 요청이 와도 정상적으로 저장이 된다.")
    @Test
    void saveUserConcurrentTest() throws ExecutionException, InterruptedException {
        final int threadCount = 10;
        final int saveUserCountPerThread = 100;
        final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < saveUserCountPerThread; j++) {
                    userRepository.save(new User("test123", "이성빈", "password123123", "sungbin@naver.com"));
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        executorService.shutdown();

        assertThat(userRepository.findAll().size()).isEqualTo(1000);
    }


    @DisplayName("회원 저장을 하면 Id 값이 반환 된다.")
    @Test
    void saveTest() {
        User user = new User("testId", "이성빈", "test123123", "sungbin@naver.com");

        final Long id = userRepository.save(user);

        assertThat(id).isEqualTo(1L);
    }

    @DisplayName("회원 조회 시 Optional로 랩핑해서 반환 된다.")
    @Test
    void findUserSuccessTest() {
        User user = new User("testId", "이성빈", "test123123", "sungbin@naver.com");
        final Long id = userRepository.save(user);

        final Optional<User> userOptional = userRepository.findById(id);

        assertThat(userOptional).isPresent();
        assertThat(userOptional.get()).isInstanceOf(User.class);
    }

    @DisplayName("유효하지 않은 Id로 조회 시 Optional에 null로 반환 된다.")
    @Test
    void findUserInvalidIdTest() {
        final Optional<User> userOptional = userRepository.findById(1L);

        assertThat(userOptional).isEmpty();
    }
}