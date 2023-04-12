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
// TODO: DB에 테스트했던 애들이 남지는 않으나, customerId가 올라간 채로 있는 것을 볼 수 있다. 왜 그런지 확인하자.
class UserServiceTest {
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    void 회원가입() {
        // given
        User user = new User();
        user.setName("springName");
        user.setUserId("springId");

        // when
        String saveUserId = userService.join(user);

        // then
        UserResponse findUser = userService.findByUserId(saveUserId).get();
        assertThat(user.getName()).isEqualTo(findUser.getName());
    }

    @Test
    void 중복_회원_이름_예외() {
        // given
        User user1 = new User();
        user1.setName("springName1");
        user1.setUserId("springId1");

        User user2 = new User();
        user2.setName("springName1");
        user2.setUserId("springId2");

        userService.join(user1);

        // when, then
        assertThatThrownBy(()-> {
            userService.join(user2);
        }).isInstanceOf(IllegalStateException.class).hasMessage("이미 존재하는 이름입니다.");
    }

    @Test
    void 중복_회원_아이디_예외() {
        // given
        User user1 = new User();
        user1.setName("springName1");
        user1.setUserId("springId1");

        User user2 = new User();
        user2.setName("springName2");
        user2.setUserId("springId1");


        // when
        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
    }

    @Test
    @DisplayName("Transaction 테스트: join()을 실패 하면 AtomicLong()이 올라가지 않는다.")
    void join_transaction(){
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
        assertThat(user3.getCustomerId()).isEqualTo(user1.getCustomerId()+1L);
    }
}
