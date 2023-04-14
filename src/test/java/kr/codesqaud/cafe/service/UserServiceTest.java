package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.user.UserResponse;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원 가입 성공")
    void join() {
        // given
        User user1 = new User();
        user1.setName("springName1");
        user1.setUserId("springId1");
        user1.setPassword("1234");
        user1.setEmail("jian@gmail.com");

        // when
        String saveUserId = userService.join(user1);

        // then
        UserResponse findUser = userService.findByUserId(saveUserId).get();
        assertThat(user1.getName()).isEqualTo(findUser.getName());
    }

    @Test
    @DisplayName("중복 회원 이름 예외")
    void userName() {
        // given
        User user1 = new User();
        user1.setName("springName1");
        user1.setUserId("springId1");
        user1.setPassword("1234");
        user1.setEmail("jian@gmail.com");

        User user2 = new User();
        user2.setName("springName1");
        user2.setUserId("springId2");
        user2.setPassword("1234");
        user2.setEmail("jian@gmail.com");

        userService.join(user1);

        // when, then
        assertThatThrownBy(() -> {
            userService.join(user2);
        }).isInstanceOf(IllegalStateException.class).hasMessage("이미 존재하는 이름입니다.");
    }

    @Test
    @DisplayName("중복 회원 아이디 예외")
    void userId() {
        // given
        User user1 = new User();
        user1.setName("springName1");
        user1.setUserId("springId1");
        user1.setPassword("1234");
        user1.setEmail("jian@gmail.com");

        User user2 = new User();
        user2.setName("springName2");
        user2.setUserId("springId1");
        user2.setPassword("1234");
        user2.setEmail("jian@gmail.com");


        // when
        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
    }

    @Test
    @DisplayName("Transaction 테스트: join()을 실패 하면 AtomicLong()이 올라가지 않는다.")
    void join_transaction() {
        // given
        User user1 = new User();
        user1.setName("springName1");
        user1.setUserId("springId1");
        user1.setPassword("1234");
        user1.setEmail("jian@gmail.com");

        User user2 = new User();
        user2.setName("springName2");
        user2.setUserId("springId1");
        user2.setPassword("1234");
        user2.setEmail("jian@gmail.com");

        User user3 = new User();
        user3.setName("springName3");
        user3.setUserId("springId3");
        user3.setPassword("1234");
        user3.setEmail("jian@gmail.com");

        userService.join(user1);

        // when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
        userService.join(user3);

        // then
        assertThat(user3.getCustomerId()).isEqualTo(user1.getCustomerId() + 1L);
    }
}
