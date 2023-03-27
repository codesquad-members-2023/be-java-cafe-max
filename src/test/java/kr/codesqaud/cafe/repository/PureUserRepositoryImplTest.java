package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PureUserRepositoryImplTest {
    PureUserRepositoryImpl userRepository = new PureUserRepositoryImpl();

    @DisplayName("회원 저장을 하면 Id 값이 반환 된다.")
    @Test
    void save() {
        User user = new User("testId", "이성빈", "test123123", "sungbin@naver.com");

        final Long id = userRepository.save(user);

        assertThat(id).isEqualTo(1L);
    }
}