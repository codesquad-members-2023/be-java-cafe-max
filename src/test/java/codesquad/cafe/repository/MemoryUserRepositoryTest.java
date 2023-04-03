package codesquad.cafe.repository;

import codesquad.cafe.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
    void findById() {
        // given

        // when

        // then
    }

    @Test
    void findAll() {
        // given

        // when

        // then
    }
}