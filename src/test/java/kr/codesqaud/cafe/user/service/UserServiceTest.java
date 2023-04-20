package kr.codesqaud.cafe.user.service;

import kr.codesqaud.cafe.user.controller.response.UserListResponse;
import kr.codesqaud.cafe.user.controller.response.UserProfileResponse;
import kr.codesqaud.cafe.user.repository.JdbcUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
class UserServiceTest {

    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void init() {
        userService = new UserService(new JdbcUserRepository(dataSource));
    }

    @Test
    @DisplayName("회원 가입 성공")
    void registerSuccess() {
        // given
        User user = new User(null, "user1", "1234", "test1", "test@test.com");

        // when
        boolean result = userService.register(user);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("회원 가입 실패")
    void registerFail() {
        // given
        User user = new User(null, "user1", "1234", "test1", "test@test.com");
        userService.register(user);

        // when
        boolean result = userService.register(user);

        // then
        assertFalse(result);
    }

    @Test
    void getUserList() {
        // given
        userService.register(new User(null, "user1", "1234", "test1", "test@test.com"));
        userService.register(new User(null, "user2", "1234", "test2", "test2@test.com"));

        // when
        List<UserListResponse> users = userService.getUserList();

        // then
        assertEquals(2, users.size());
    }

    @Test
    void getProfile() {
        // given
        userService.register(new User(null, "user1", "1234", "test1", "test@test.com"));
        userService.register(new User(null, "user2", "1234", "test2", "test2@test.com"));

        // when
        UserProfileResponse user = userService.getProfile("user1");

        // then
        assertEquals("test1", user.getName());
        assertEquals("test@test.com", user.getEmail());
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {
        // given
        userService.register(new User(null, "user1", "1234", "test1", "test@test.com"));

        // when
        Optional<User> loginUser = userService.login("user1", "1234");

        // then
        assertTrue(loginUser.isPresent());
        assertEquals("user1", loginUser.get().getUserId());
    }

    @Test
    @DisplayName("로그인 실패 : 비밀번호 오류")
    void loginFailByPassword() {
        // given
        userService.register(new User(null, "user1", "1234", "test1", "test@test.com"));

        // when
        Optional<User> loginUser = userService.login("user1", "5678");

        // then
        assertTrue(loginUser.isEmpty());
    }

    @Test
    @DisplayName("로그인 실패 : 없는 아이디")
    void loginFailByUserId() {
        // given
        userService.register(new User(null, "user1", "1234", "test1", "test@test.com"));

        // when
        Optional<User> loginUser = userService.login("user2", "1234");

        // then
        assertTrue(loginUser.isEmpty());
    }
}