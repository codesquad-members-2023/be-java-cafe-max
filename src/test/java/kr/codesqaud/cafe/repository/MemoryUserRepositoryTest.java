package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class MemoryUserRepositoryTest {
    MemoryUserRepository repository = new MemoryUserRepository();

    @DisplayName("Map에 저장이 잘 되는지 테스트")
    @Test
    void save() {
        User user = new User();
        user.setName("sully");

        repository.save(user);

        User result = repository.findByUserId(user.getUserId()).get();
        assertThat(result).isEqualTo(user);
    }

//    @Test
//    void findAll() {
//        User user1 = new User();
//        user1.setName("sully1");
//        repository.save(user1);
//
//        User user2 = new User();
//        user2.setName("sully2");
//        repository.save(user2);
//
//        List<User> result = repository.findAll();
//
//        assertThat(result.size()).isEqualTo(2);
//    }
}