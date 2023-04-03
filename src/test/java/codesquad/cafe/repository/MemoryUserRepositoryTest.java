package codesquad.cafe.repository;

import codesquad.cafe.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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
    void findAll() {
        // given

        // when

        // then
    }
}