package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserService userService;
    MemoryUserRepository userRepository;

    @BeforeEach
    public void beforeEach(){
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void afterEach(){
        userRepository.clearStore();
    }
    @Test
    void 회원가입() {
        // given
        User user = new User();
        user.setName("spring");

        // when
        Long saveNumber = userService.join(user);

        // then
        User findUser = userService.findOne(saveNumber).get();
        assertThat(user.getName()).isEqualTo(findUser.getName());
    }

    @Test
    public void 중복_회원_예외(){
        // given
        User user1 = new User();
        user1.setName("spring");

        User user2 = new User();
        user2.setName("spring");

        // when
        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
