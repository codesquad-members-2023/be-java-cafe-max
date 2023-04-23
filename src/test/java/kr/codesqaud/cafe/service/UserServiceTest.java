package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.user.UserForm;
import kr.codesqaud.cafe.controller.user.UserResponse;
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
        UserForm user1 = new UserForm("springId1", "1234", "springName1", "test@gmail.com");

        // when
        String saveUserId = userService.join(user1);

        // then
        UserResponse findUser = userService.findByUserId(saveUserId);
        assertThat(user1.getName()).isEqualTo(findUser.getName());
    }

    @Test
    @DisplayName("중복 회원 이름 예외")
    void userName() {
        // given
        UserForm user1 = new UserForm("springId1", "1234", "springName1", "test@gmail.com");
        UserForm user2 = new UserForm("springId2", "1234", "springName2", "test@gmail.com");
        user2.setName("springName1");

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
        UserForm user1 = new UserForm("springId1", "1234", "springName1", "test@gmail.com");
        UserForm user2 = new UserForm("springId2", "1234", "springName2", "test@gmail.com");
        user2.setUserId("springId1");


        // when
        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
    }
}
