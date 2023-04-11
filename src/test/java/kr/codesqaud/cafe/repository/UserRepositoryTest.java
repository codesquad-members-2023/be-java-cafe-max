package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.MemoryUserRepository;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class UserRepositoryTest {
    UserRepository repository = new MemoryUserRepository();

    // 테스트 순서는 보장될 수 없기 때문에 테스트 코드 함수의 실행 순서에 의존하면 안된다.
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void save(){
        // given
        User user = new User();
        user.setName("springName");
        user.setUserId("springId");

        // when
        repository.save(user);
        User result = repository.findByUserId(user.getUserId()).get();

        //then
        assertThat(user).isEqualTo(result);
    }

    @Test
    void findByName(){
        User user1 = new User();
        user1.setName("springName1");
        user1.setUserId("springId1");
        repository.save(user1);

        User user2 = new User();
        user2.setName("springName2");
        user2.setUserId("springId2");
        repository.save(user2);

        User result = repository.findByName("springName1").get();
        assertThat(result).isEqualTo(user1);
    }

    @Test
    void findByUserId(){
        User user1 = new User();
        user1.setName("springName1");
        user1.setUserId("springId1");
        repository.save(user1);

        User user2 = new User();
        user2.setName("springName2");
        user2.setUserId("springId2");
        repository.save(user2);

        User result = repository.findByUserId("springId1").get();
        assertThat(result).isEqualTo(user1);
    }

    @Test
    public void findAll(){
        User user1 = new User();
        user1.setName("springName1");
        user1.setUserId("springId1");
        repository.save(user1);

        User user2 = new User();
        user2.setName("springName2");
        user2.setUserId("springId2");
        repository.save(user2);

        List<User> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
