package kr.codesqaud.cafe.service;

import static org.assertj.core.api.Assertions.assertThat;

import kr.codesqaud.cafe.login.LoginRequestDto;
import kr.codesqaud.cafe.login.LoginService;
import kr.codesqaud.cafe.user.User;
import kr.codesqaud.cafe.user.UserRepository;
import kr.codesqaud.cafe.user.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

// TODO: 세션 유지 여부를 판단할 수는 없을까?

@JdbcTest
class LoginServiceTest {

    LoginService loginService;
    UserRepository userRepository;

    @Autowired // 테스트 시 AutoWired 필수, 안하면 에러 발생. 아마 datasource가 주입이 안돼서 그런게 아닐까 추측
    NamedParameterJdbcTemplate template;

    @BeforeEach
    void beforeEach() {
        userRepository = new UserRepositoryImpl(template);
        loginService = new LoginService(userRepository);
    }

    // TODO: NamedParameterJdbcTemplate를 사용하니까 데이터 지우는 방법을 모르겠다..
//    @AfterEach
//    void afterEach(User user) {
//        String sql = "delete from users";
//        Map<String, Object> params = Collections.emptyMap();
//        template.update(sql, params);
//    }

    @Test
    @DisplayName("가입된 ID로 로그인할 때 비밀번호가 맞다면 로그인이 성공한다.")
    void loginSuccess() {
        // given
        User user = new User("jinny", "11110000", "지니", "jinny@gmail.com");
        userRepository.save(user);

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUserId("jinny");
        loginRequestDto.setPassword("11110000");

        // when
        User loginUser = loginService.login(loginRequestDto);

        // then
        assertThat(loginUser.getUserId()).isEqualTo(user.getUserId());
        assertThat(loginUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @DisplayName("가입된 ID로 로그인할 때 비밀번호가 틀리다면 로그인이 실패한다.")
    void loginFailedByWrongPassword() {
        // given
        User user = new User("tester", "11110000", "테스터", "tester@gmail.com");
        userRepository.save(user);

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUserId("tester");
        loginRequestDto.setPassword("11111111");

        // when
        User loginUser = loginService.login(loginRequestDto);

        // then
        assertThat(loginUser).isEqualTo(null);
    }

    @Test
    @DisplayName("가입되지 않은 ID로 로그인하면 로그인이 실패한다.")
    void loginFailedByUnregisteredUser() {
        // given
        User user = new User("tester1", "11110000", "테스터", "tester@gmail.com");
        userRepository.save(user);

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUserId("tester3");
        loginRequestDto.setPassword("11111111");

        // when
        User loginUser = loginService.login(loginRequestDto);

        // then
        assertThat(loginUser).isEqualTo(null);
    }
}
