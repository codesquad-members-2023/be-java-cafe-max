package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
class JdbcUserRepositoryTest {

    private UserRepository userRepository;
    @Autowired DataSource dataSource;

    @BeforeEach
    void setUserRepository() {
        userRepository = new JdbcTemplateUserRepository(dataSource);
    }

    @Test
    @DisplayName("repository에 user가 저장되는지 테스트")
    void saveTest() {
        User user = new User("testId", "1234","test_name", "test@1234");
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getUserId()).isEqualTo("testId");
        assertThat(savedUser.getPassword()).isEqualTo("1234");
        assertThat(savedUser.getName()).isEqualTo("test_name");
        assertThat(savedUser.getEmail()).isEqualTo("test@1234");
    }

    @Test
    @DisplayName("id로 회원 조회 테스트")
    void findByIdTest() {
        User user = userRepository.findById("Joy").get();

        assertThat(user.getUserId()).isEqualTo("Joy");
        assertThat(user.getPassword()).isEqualTo("1234");
        assertThat(user.getName()).isEqualTo("Joy");
        assertThat(user.getEmail()).isEqualTo("123@456.com");
    }

    @Test
    @DisplayName("name으로 회원 조회 테스트")
    void findByNameTest() {
        User user1 = new User("testId", "1234","test_name", "test@1234");
        userRepository.save(user1);

        User user2 = userRepository.findByName("test_name").get();
        assertThat(user2.getUserId()).isEqualTo("testId");
        assertThat(user2.getPassword()).isEqualTo("1234");
        assertThat(user2.getName()).isEqualTo("test_name");
        assertThat(user2.getEmail()).isEqualTo("test@1234");
    }

    @Test
    @DisplayName("모든 회원 조회 테스트")
    void findAllTest() {
        User user = new User("testId", "1234","test_name", "test@1234");
        userRepository.save(user);

        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(2);
    }
}