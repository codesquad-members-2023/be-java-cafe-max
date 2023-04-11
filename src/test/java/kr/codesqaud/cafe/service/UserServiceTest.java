package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserService userService;
    MemoryUserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
    }

    @AfterEach
    void afterEach() {
        userRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        User user = new User();
        user.setName("springName");
        user.setUserId("springId");

        // when
        String saveUserId = userService.join(user);

        // then
        User findUser = userService.findOne(saveUserId).get();
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
}
