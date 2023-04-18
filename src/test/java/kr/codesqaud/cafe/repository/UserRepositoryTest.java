package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.MemoryUserRepository;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserRepositoryTest {
    UserRepository repository = new MemoryUserRepository();

    @AfterEach
    void clear(){
        repository.clearStore();
    }

    @Test
    @DisplayName("저장된 User가 생성한 User와 같은지 확인")
    void save() {
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
    @DisplayName("저장된 User가 생성한 User와 같은지 name으로 확인")
    void findByName() {
        // given
        User user1 = new User();
        user1.setName("springName1");
        user1.setUserId("springId1");
        repository.save(user1);

        User user2 = new User();
        user2.setName("springName2");
        user2.setUserId("springId2");
        repository.save(user2);

        // when
        User result = repository.findByName("springName1").get();

        // then
        assertThat(result).isEqualTo(user1);
    }

    @Test
    @DisplayName("저장된 User가 생성한 User와 같은지 userId로 확인")
    void findByUserId() {
        // given
        User user1 = new User();
        user1.setName("springName1");
        user1.setUserId("springId1");
        repository.save(user1);

        User user2 = new User();
        user2.setName("springName2");
        user2.setUserId("springId2");
        repository.save(user2);

        // when
        User result = repository.findByUserId("springId1").get();

        // then
        assertThat(result).isEqualTo(user1);
    }

    @Test
    @DisplayName("모든 User가 잘 저장 되었는지 확인")
    public void findAll() {
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
