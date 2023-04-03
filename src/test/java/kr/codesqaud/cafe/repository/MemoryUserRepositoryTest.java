package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryUserRepositoryTest {
    MemoryUserRepository repository = new MemoryUserRepository();

    // 테스트 순서는 보장될 수 없기 때문에 테스트 코드 함수의 실행 순서에 의존하면 안된다.
    @AfterEach // 함수가 끝날 때마다 afterEach가 호출된다.
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save(){
        User user = new User();
        user.setName("spring");
        
        repository.save(user);

        // optinal은 get()으로 꺼낼 수 있지만, 원래는 바로 꺼내면 안된다.
        User result = repository.findByUserId(user.getUserId()).get();

        assertThat(user).isEqualTo(result);
    }

    @Test
    public void findByName(){
        User user1 = new User();
        user1.setName("spring1");
        repository.save(user1);

        User user2 = new User();
        user2.setName("spring1");
        repository.save(user2);

        User result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(user1);
    }

    @Test
    public void findAll(){
        User user1 = new User();
        user1.setName("spring1");
        repository.save(user1);

        User user2 = new User();
        user2.setName("spring1");
        repository.save(user2);

        List<User> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
