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
        User user = new User("testId", "1234","test_name", "test@123");
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getUserId()).isEqualTo("testId");
        assertThat(savedUser.getPassword()).isEqualTo("1234");
        assertThat(savedUser.getName()).isEqualTo("test_name");
        assertThat(savedUser.getEmail()).isEqualTo("test@123");
    }

    @Test
    @DisplayName("id로 회원 조회 테스트")
    void findByIdTest() {
        User savedUser = userRepository.save(new User("testId", "1234","test_name", "test@123"));

        User user = userRepository.findById("testId").get();

        assertThat(user.getUserId()).isEqualTo("testId");
        assertThat(user.getPassword()).isEqualTo("1234");
        assertThat(user.getName()).isEqualTo("test_name");
        assertThat(user.getEmail()).isEqualTo("test@123");
    }

    @Test
    @DisplayName("name으로 회원 조회 테스트")
    void findByNameTest() {
        userRepository.save(new User("testId", "1234","test_name", "test@123"));

        User user = userRepository.findByName("test_name").get();

        assertThat(user.getUserId()).isEqualTo("testId");
        assertThat(user.getPassword()).isEqualTo("1234");
        assertThat(user.getName()).isEqualTo("test_name");
        assertThat(user.getEmail()).isEqualTo("test@123");
    }

    @Test
    @DisplayName("모든 회원 조회 테스트")
    void findAllTest() {
        userRepository.save(new User("testId", "1234","test_name", "test@123"));

        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);
    }
}