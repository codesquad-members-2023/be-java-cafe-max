package kr.codesqaud.cafe.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MemoryUserRepositoryTest {

    MemoryUserRepository memoryUserRepository = new MemoryUserRepository();

    @Test
    void save() {
        //given
        User user = new User("hana", "1234", "하나", "dd@dd");

        //when
        memoryUserRepository.save(user);
        User findUser = memoryUserRepository.findById("hana");

        //then
        Assertions.assertThat(user).isEqualTo(findUser);
    }

    @Test
    void findById() {
    }
}